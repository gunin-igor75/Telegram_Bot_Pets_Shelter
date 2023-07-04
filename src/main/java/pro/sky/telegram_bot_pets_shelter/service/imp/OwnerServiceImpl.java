package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;

import java.time.LocalDate;
import java.util.List;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner createOwner(Owner owner) {
        checkOwnerNull(owner);
        if (owner.getId() == null) {
            return ownerRepository.save(owner);
        }
        return owner;
    }

    @Override
    public Owner findOwner(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner findOwnerByChatId(Long id) {
        return ownerRepository.findByChatId(id);
    }

    @Override
    public Owner editOwner(Owner owner) {
        checkOwnerNull(owner);
        Owner persistentOwner = findOwner(owner.getId());
        if (persistentOwner == null) {
            throw new OwnerNotFoundException();
        }
        return ownerRepository.save(owner);
    }

    @Override
    public Owner deleteOwner(Long id) {
        Owner owner = findOwner(id);
        if (owner == null) {
            throw new OwnerNotFoundException();
        }
        ownerRepository.delete(owner);
        return owner;
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner findOrSaveOwner(User telegramUser) {
        Long chatId = telegramUser.getId();
        Owner persistentOwner = findOwnerByChatId(chatId);
        if (persistentOwner == null) {
            Owner transientOwner = Owner.builder()
                    .chatId(chatId)
                    .firstname(telegramUser.getFirstName())
                    .lastname(telegramUser.getLastName())
                    .username(telegramUser.getUserName())
                    .registration(false)
                    .state(BASIC_STATE)
                    .build();
            persistentOwner = createOwner(transientOwner);
        }
        return persistentOwner;
    }

    @Override
    public String registration(Long chatId) {
        var persistentOwner = findOwnerByChatId(chatId);
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        String text = "Congratulations. You have successfully registered";
        if (persistentOwner.getRegistration()) {
            text = "Sorry. You are already registered";
        } else {
            persistentOwner.setRegistration(true);
            persistentOwner.setDateRegistration(LocalDate.now());
            editOwner(persistentOwner);
        }
        return text;
    }

    @Override
    public boolean checkNoAdoptionCat(Owner owner) {
        long id = owner.getId();
        String catName = ownerRepository.getCatNoAdoption(id);
        return catName == null;
    }

    @Override
    public boolean checkNoAdoptionDog(Owner owner) {
        long id = owner.getId();
        String dogName = ownerRepository.getDogNoAdoption(id);
        return dogName == null;
    }

    @Override
    public List<Owner> getOwnerCatsEndTestPeriod(LocalDate date) {
        return ownerRepository.getOwnerCatsEndTestPeriod(date);
    }

    @Override
    public List<Owner> getOwnerDogsEndTestPeriod(LocalDate date) {
        return ownerRepository.getOwnerDogsEndTestPeriod(date);
    }

    private void checkOwnerNull(Owner owner) {
        if (owner == null) {
            log.error("owner is null");
            throw new NullPointerException();
        }
    }
}
