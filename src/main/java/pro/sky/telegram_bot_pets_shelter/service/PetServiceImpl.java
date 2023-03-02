package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;
import pro.sky.telegram_bot_pets_shelter.repositories.PetRepository;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    public PetServiceImpl(PetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return null;
    }

    @Override
    public Pet findPet(Long id) {
        return null;
    }

    @Override
    public Pet editPet(Pet faculty) {
        return null;
    }

    @Override
    public Pet deletePet(Long id) {
        return null;
    }

    @Override
    public List<Pet> getAllPets() {
        return null;
    }
}
