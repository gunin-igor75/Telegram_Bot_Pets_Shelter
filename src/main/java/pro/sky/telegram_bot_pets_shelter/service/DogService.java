package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Report;

import java.time.LocalDate;
import java.util.List;

public interface DogService {

    Dog createDog(Dog dog);

    Dog findDog(Long id);

    Dog editDog(Dog dog);

    Dog deleteDog(Long id);

    List<Dog> getAllDogs();

    List<Dog> getAllDogsFree();

    List<Dog> getDogsByAdoptedIsFalse(LocalDate date);

    List<Report> getReportMaxDate();
}
