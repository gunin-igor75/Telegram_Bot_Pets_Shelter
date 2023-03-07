package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public boolean createOwner(Owner owner) {
        Optional<Owner> newOwner = ownerRepository.findByChatId(owner.getChatId());
        if (newOwner.isPresent()) {
            return false;
        }
        ownerRepository.save(owner);
        return true;
    }

    @Override
    public Optional<Owner> findOwnerById(long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Optional<Owner> findOwnerByChatId(long id) {
        return ownerRepository.findByChatId(id);
    }

    @Override
    public Owner editOwner(Owner owner) {
        Optional<Owner> newOwner = findOwnerById(owner.getId());
        if (newOwner.isEmpty()) {
            throw new OwnerNotFoundException();
        }
        return ownerRepository.save(owner);
    }

    @Override
    public Owner DeleteOwner(Long id) {
        return null;
    }

    @Override
    public List<Owner> getAllOwners() {
        return null;
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
}
