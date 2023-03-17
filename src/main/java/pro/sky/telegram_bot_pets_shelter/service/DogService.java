package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Dog;

import java.util.List;

public interface DogService {

    Dog createDog(Dog dog);

    Dog findDog(Long id);

    Dog editDog(Dog dog);

    Dog deleteDog(Long id);

    List<Dog> getAllDogs();

    List<Dog> getAllDogsFree();
}
