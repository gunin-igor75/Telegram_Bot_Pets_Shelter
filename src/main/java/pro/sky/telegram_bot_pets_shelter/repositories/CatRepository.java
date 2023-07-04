package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;

import java.time.LocalDate;
import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {

    List<Cat> getCatsByAdoptedIsNull();

    List<Cat> getCatsByAdoptedIsFalseAndDateAdoptionBefore(LocalDate date);

}
