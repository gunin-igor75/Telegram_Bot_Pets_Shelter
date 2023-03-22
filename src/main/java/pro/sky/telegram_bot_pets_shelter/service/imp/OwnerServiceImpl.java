package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;

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
        return ownerRepository.findByChatId(id).orElse(null);
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
        Owner persistentOwner = findOwnerByChatId(telegramUser.getId());
        if (persistentOwner == null) {
            Owner transientOwner = Owner.builder()
                    .chatId(telegramUser.getId())
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
    public String registration(long chatId) {
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
    public void editOwnerState(long id, UserState state) {
        var persistentOwner = findOwnerByChatId(id);
        if (persistentOwner == null) {
            log.error("persistentOwner is nul");
            throw new OwnerNotFoundException();
        }
        persistentOwner.setState(state);
        editOwner(persistentOwner);
    }

    @Override
    public boolean checkAdoptionCat(Owner owner) {
        long id = owner.getId();
        List<String> cats = ownerRepository.getCatAdoption(id);
        return cats.isEmpty();
    }

    @Override
    public boolean checkAdoptionDog(Owner owner) {
        long id = owner.getId();
        List<String> dogs = ownerRepository.getDogAdoption(id);
        return dogs.isEmpty();
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
