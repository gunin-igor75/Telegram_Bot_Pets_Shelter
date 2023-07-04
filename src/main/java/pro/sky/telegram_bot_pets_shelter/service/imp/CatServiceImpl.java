package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;
import pro.sky.telegram_bot_pets_shelter.service.CatService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private LocalDate currentDate;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat createCat(Cat cat) {
        checkCayNull(cat);
        if (cat.getId() == null) {
            return catRepository.save(cat);
        }
        return cat;
    }

    @Override
    public Cat findCat(Long id) {
        return catRepository.findById(id).orElse(null);
    }

    @Override
    public Cat editCat(Cat cat) {
        checkCayNull(cat);
        Cat persistentCat = findCat(cat.getId());
        if (persistentCat == null) {
            throw new CatNotFoundException();
        }
        return catRepository.save(cat);
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

    @Override
    public List<Cat> getCatsByAdoptedAndDateAdoptionBefore(LocalDate date) {
        return catRepository.getCatsByAdoptedIsFalseAndDateAdoptionBefore(date);
    }

    @Override
    public List<Report> getReportMaxDate() {
        currentDate = LocalDate.now();
        List<Cat> catsAdopted = getCatsByAdoptedAndDateAdoptionBefore(currentDate);
        List<Report> reports = new ArrayList<>();
        for (Cat cat : catsAdopted) {
            Report report = cat.getReport()
                    .stream()
                    .max(Comparator.comparing(Report::getDateReport)).orElse(null);
            assert report != null;
            reports.add(report);
        }
        return reports;
    }
    private void checkCayNull(Cat cat) {
        if (cat == null) {
            log.error("pet is null");
            throw new NullPointerException();
        }
    }
}
