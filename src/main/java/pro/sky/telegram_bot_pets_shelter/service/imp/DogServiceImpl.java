package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;
import pro.sky.telegram_bot_pets_shelter.service.DogService;

import java.util.List;

@Service
@Slf4j
public class DogServiceImpl implements DogService {
    final DogRepository dogRepository;
    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public Dog createDog(Dog dog) {
        checkDogNull(dog);
        if (dog.getId() == null) {
            return dogRepository.save(dog);
        }
        return dog;
    }

    @Override
    public Dog findDog(Long id) {
        return dogRepository.findById(id).orElse(null);
    }

    @Override
    public Dog editDog(Dog dog) {
        checkDogNull(dog);
        Dog persistentDog = findDog(dog.getId());
        if (persistentDog == null) {
            throw new DogNotFoundException();
        }
        return dogRepository.save(persistentDog);
    }

    @Override
    public Dog deleteDog(Long id) {
        Dog dog = findDog(id);
        if (dog == null) {
            throw new DogNotFoundException();
        }
        dogRepository.delete(dog);
        return dog;
    }

    @Override
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    @Override
    public List<Dog> getAllDogsFree() {
        return dogRepository.getDogsByAdoptedIsNull();
    }

    private void checkDogNull(Dog dog) {
        if (dog == null) {
            log.error("dog is null");
            throw new NullPointerException();
        }
    }
}
