package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VisitorNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;
import pro.sky.telegram_bot_pets_shelter.service.CatService;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat createCat(Cat cat) {
        Cat persistentCat = findCat(cat.getId());
        if (persistentCat == null) {
            persistentCat = catRepository.save(cat);
        }
        return persistentCat;
    }

    @Override
    public Cat findCat(Long id) {
        return catRepository.findById(id).orElse(null);
    }

    @Override
    public Cat editCat(Cat cat) {
        Cat persistentCat = findCat(cat.getId());
        if (persistentCat == null) {
            throw new CatNotFoundException();
        }
        return catRepository.save(persistentCat);
    }

    @Override
    public Cat deleteCat(Long id) {
        Cat cat = findCat(id);
        if (cat == null) {
            throw new CatNotFoundException();
        }
        catRepository.delete(cat);
        return cat;
    }

    @Override
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }
    @Override
    public List<Cat> getAllCatsFree() {
        return catRepository.getCatsByAdoptedIsNull();
    }
}
