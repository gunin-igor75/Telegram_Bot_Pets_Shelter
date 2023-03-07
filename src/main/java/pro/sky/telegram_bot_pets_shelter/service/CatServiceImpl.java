package pro.sky.telegram_bot_pets_shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat createCat(Cat cat) {
        return null;
    }

    @Override
    public Optional<Cat> findCat(Long id) {
        return catRepository.findById(id);
    }

    @Override
    public Cat editCat(Cat cat) {
        Optional<Cat> newCat = findCat(cat.getId());
        if (newCat.isEmpty()) {
            throw new CatNotFoundException();
        }
        return catRepository.save(cat);
    }

    @Override
    public Cat deleteCat(Long id) {
        return null;
    }

    @Override
    public List<Cat> getAllCatsFree() {
        return catRepository.getCatsByAdoptedIsNull();
    }
}
