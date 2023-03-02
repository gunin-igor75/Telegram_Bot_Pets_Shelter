package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Pet;

import java.util.List;

public interface PetService {

    Pet createPet(Pet pet);

    Pet findPet(Long id);

    Pet editPet(Pet faculty);

    Pet deletePet(Long id);

    List<Pet> getAllPets();

}
