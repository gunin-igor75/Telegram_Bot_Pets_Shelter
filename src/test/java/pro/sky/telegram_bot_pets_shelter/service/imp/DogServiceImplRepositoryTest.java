package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DogServiceImplRepositoryTest {

    @Autowired
    private DogServiceImpl dogService;

    @Test
    void getAllDogsFree() {
        givenDogsFree();
        List<Dog> dogs = dogService.getAllDogsFree();
        thenAllDogsFree(dogs);
    }

    private void givenDogsFree() {
        Dog bobick = createDogAdoptedNull("Bobick");
        Dog tom = createDogAdoptedNull("Tom");
        Dog sharick = createDogAdopted("Sharick", LocalDate.now());
    }

    private void thenAllDogsFree(List<Dog> actualDogs) {
        Dog bobick = dogService.findDog(1L);
        Dog tom = dogService.findDog(2L);
        List<Dog> expectedDogs = List.of(bobick, tom);
        assertThat(actualDogs).containsExactlyInAnyOrderElementsOf(expectedDogs);
        assertThat(actualDogs.size()).isEqualTo(2);
    }

    @Test
    public void getDogsByAdoptedAndDateAdoptionBeforeTest() {
        givenDogsByAdoptedAndDateAdoptionBefore();
        List<Dog> dogs = dogService.getDogsByAdoptedAndDateAdoptionBefore(LocalDate.now());
        thenDogsByAdoptedAndDateAdoptionBefore(dogs);
    }

    private void givenDogsByAdoptedAndDateAdoptionBefore() {
        Dog bobick = createDogAdoptedNull("Bobick");
        Dog tom = createDogAdopted("Tom", LocalDate.now());
        LocalDate dateAdoptionFirst = LocalDate.now().minusDays(1);
        LocalDate dateAdoptionSecond = LocalDate.now().minusDays(2);
        Dog sema = createDogAdopted("sema", dateAdoptionFirst);
        Dog tema = createDogAdopted("tema", dateAdoptionSecond);
    }

    private void thenDogsByAdoptedAndDateAdoptionBefore(List<Dog> actualDogs) {
        Dog dogFirst = dogService.findDog(3L);
        Dog dogSecond = dogService.findDog(4L);
        List<Dog> expectedDogs = List.of(dogFirst, dogSecond);
        assertThat(actualDogs).containsExactlyInAnyOrderElementsOf(expectedDogs);
        assertThat(actualDogs.size()).isEqualTo(2);
    }

    private Dog createDogAdoptedNull(String name) {
        Dog dog = Dog.builder()
                .testPeriod(30)
                .name(name)
                .build();
        return dogService.createDog(dog);
    }

    private Dog createDogAdopted(String name, LocalDate dateAdoption) {
        Dog dog = Dog.builder()
                .testPeriod(30)
                .name(name)
                .dateAdoption(dateAdoption)
                .adopted(false)
                .build();
        return dogService.createDog(dog);
    }


}