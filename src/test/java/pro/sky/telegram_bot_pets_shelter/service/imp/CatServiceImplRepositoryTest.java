package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CatServiceImplRepositoryTest {
    @Autowired
    private CatServiceImpl catService;

    @Test
    void getAllCatsFree() {
        givenCatsFree();
        List<Cat> cats = catService.getAllCatsFree();
        thenAllCatsFree(cats);
    }

    private void givenCatsFree() {
        Cat barsick = createCatAdoptedNull("Barsick");
        Cat tom = createCatAdoptedNull("Tom");
        Cat mur = createCatAdopted("Mur", LocalDate.now());
    }

    private void thenAllCatsFree(List<Cat> actualCats) {
        Cat barsick = catService.findCat(1L);
        Cat tom = catService.findCat(2L);
        List<Cat> expectedCats = List.of(barsick, tom);
        assertThat(actualCats).containsExactlyInAnyOrderElementsOf(expectedCats);
        assertThat(actualCats.size()).isEqualTo(2);
    }

    @Test
    public void getCatsByAdoptedAndDateAdoptionBeforeTest() {
        givenCatsByAdoptedAndDateAdoptionBefore();
        List<Cat> cats = catService.getCatsByAdoptedAndDateAdoptionBefore(LocalDate.now());
        thenCatsByAdoptedAndDateAdoptionBefore(cats);
    }

    private void givenCatsByAdoptedAndDateAdoptionBefore() {
        Cat barsick = createCatAdoptedNull("Barsick");
        Cat tom = createCatAdopted("Tom", LocalDate.now());
        LocalDate dateAdoptionFirst = LocalDate.now().minusDays(1);
        LocalDate dateAdoptionSecond = LocalDate.now().minusDays(2);
        Cat mur = createCatAdopted("mur", dateAdoptionFirst);
        Cat myu = createCatAdopted("myu", dateAdoptionSecond);
    }

    private void thenCatsByAdoptedAndDateAdoptionBefore(List<Cat> actualCats) {
        Cat catFirst = catService.findCat(3L);
        Cat catSecond = catService.findCat(4L);
        List<Cat> expectedCats = List.of(catFirst, catSecond);
        assertThat(actualCats).containsExactlyInAnyOrderElementsOf(expectedCats);
        assertThat(actualCats.size()).isEqualTo(2);
    }

    private Cat createCatAdoptedNull(String name) {
        Cat cat = Cat.builder()
                .testPeriod(30)
                .name(name)
                .build();
        return catService.createCat(cat);
    }

    private Cat createCatAdopted(String name, LocalDate dateAdoption) {
        Cat cat = Cat.builder()
                .testPeriod(30)
                .name(name)
                .dateAdoption(dateAdoption)
                .adopted(false)
                .build();
        return catService.createCat(cat);
    }
}