package pro.sky.telegram_bot_pets_shelter.service;


import pro.sky.telegram_bot_pets_shelter.entity.Cat;

import java.util.List;
import java.util.Optional;

public interface CatService {

    Cat createCat(Cat cat);

    Optional<Cat> findCat(Long id);

    Cat editCat(Cat cat);

    Cat deleteCat(Long id);

    List<Cat> getAllCatsFree();
}
