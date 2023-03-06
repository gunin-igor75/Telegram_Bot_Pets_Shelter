package pro.sky.telegram_bot_pets_shelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {

    List<Cat> getCatsByAdoptedIsNull();
}
