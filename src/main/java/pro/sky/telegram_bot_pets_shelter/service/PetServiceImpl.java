package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;
import pro.sky.telegram_bot_pets_shelter.repositories.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return null;
    }

    @Override
    public Optional<Pet> findPet(Long id) {
        return petRepository.findById(id);
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
    public List<Pet> getAllPetsFree() {
        return petRepository.getPetsByAdoptedIsFalse();
    }
}
