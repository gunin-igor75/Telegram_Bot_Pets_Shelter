package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {
    final DogRepository dogRepository;

    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public Dog createDog(Dog dog) {
        return null;
    }

    @Override
    public Optional<Dog> findDog(Long id) {
        return dogRepository.findById(id);
    }

    @Override
    public Dog editDog(Dog dog) {
        return null;
    }

    @Override
    public Dog deleteDog(Long id) {
        return null;
    }

    @Override
    public List<Dog> getAllDogsFree() {
        return dogRepository.getDogsByAdoptedIsNull();
    }
}
