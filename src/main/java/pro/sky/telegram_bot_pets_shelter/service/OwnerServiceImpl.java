package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner createOwner(Owner owner) {
        return null;
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
        return null;
    }

    @Override
    public Owner DeleteOwner(Long id) {
        return null;
    }

    @Override
    public List<Owner> getAllOwners() {
        return null;
    }
}
