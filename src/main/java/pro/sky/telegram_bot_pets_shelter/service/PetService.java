package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet createPet(Pet pet);

    Optional<Pet> findPet(Long id);

    Pet editPet(Pet pet);

    Pet deletePet(Long id);

    List<Pet> getAllPetsFree();
}
