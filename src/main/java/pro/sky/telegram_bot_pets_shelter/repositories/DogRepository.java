package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;

import java.time.LocalDate;
import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {

    List<Dog> getDogsByAdoptedIsNull();

    List<Dog> getDogsByAdoptedIsFalseAndDateAdoptionBefore(LocalDate date);
}
