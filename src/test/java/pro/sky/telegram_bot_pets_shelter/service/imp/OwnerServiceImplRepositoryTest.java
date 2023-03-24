package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.DogService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OwnerServiceImplRepositoryTest {
    @Autowired
    private OwnerServiceImpl ownerService;
    @Autowired
    private CatServiceImpl catService;
    @Autowired
    private DogService dogService;
    private LocalDate currentDate;
    @Test
    void findOwnerByChatId() {
        Owner igor = getOwner("Igor", 100L);
        Owner oleg = getOwner("Oleg", 200L);
        Owner elena = getOwner("Elena", 300L);
        assertThat(ownerService.findOwnerByChatId(100L)).isEqualTo(igor);
        assertThat(ownerService.findOwnerByChatId(200L)).isEqualTo(oleg);
        assertThat(ownerService.findOwnerByChatId(300L)).isEqualTo(elena);
        assertThat(ownerService.findOwnerByChatId(400L)).isEqualTo(null);
    }

    @Test
    void checkNoAdoptionCat() {
        Owner igor = getOwner("Igor", 100L);
        Cat murka = getCat("murka", LocalDate.now());
        igor.setCat(murka);
        ownerService.editOwner(igor);
        Owner oleg = getOwner("Oleg", 200L);
        assertThat(ownerService.checkNoAdoptionCat(igor)).isFalse();
        assertThat(ownerService.checkNoAdoptionCat(oleg)).isTrue();
    }

    @Test
    void checkNoAdoptionDog() {
        Owner igor = getOwner("Igor", 100L);
        Dog boick = getDog("bobick", LocalDate.now());
        igor.setDog(boick);
        ownerService.editOwner(igor);
        Owner oleg = getOwner("Oleg", 200L);
        assertThat(ownerService.checkNoAdoptionDog(igor)).isFalse();
        assertThat(ownerService.checkNoAdoptionDog(oleg)).isTrue();
    }

    @Test
    void getOwnerCatsEndTestPeriodTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwner("Igor", 100L);
        Cat murka = getCat("murka", currentDate.minusDays(30));
        igor.setCat(murka);
        Owner ownerFirst = ownerService.editOwner(igor);
        Owner oleg = getOwner("Oleg", 200L);
        Cat myu = getCat("myu", currentDate.minusDays(29));
        oleg.setCat(myu);
        Owner ownerSecond = ownerService.editOwner(oleg);
        List<Owner> actualOwners = List.of(ownerFirst);
        List<Owner> expectedOwners = ownerService.getOwnerCatsEndTestPeriod(currentDate);
        assertThat(actualOwners).containsExactlyInAnyOrderElementsOf(expectedOwners);
        assertThat(actualOwners.size()).isEqualTo(1);
        assertThat(actualOwners.get(0)).isNotEqualTo(ownerSecond);
        assertThat(actualOwners.get(0)).isEqualTo(ownerFirst);
    }

    @Test
    void getOwnerDogsEndTestPeriodTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwner("Igor", 100L);
        Dog bim = getDog("bim", currentDate.minusDays(30));
        igor.setDog(bim);
        Owner ownerFirst = ownerService.editOwner(igor);
        Owner oleg = getOwner("Oleg", 200L);
        Dog bom = getDog("bom", currentDate.minusDays(29));
        oleg.setDog(bom);
        Owner ownerSecond = ownerService.editOwner(oleg);
        List<Owner> actualOwners = List.of(ownerFirst);
        List<Owner> expectedOwners = ownerService.getOwnerDogsEndTestPeriod(currentDate);
        assertThat(actualOwners).containsExactlyInAnyOrderElementsOf(expectedOwners);
        assertThat(actualOwners.size()).isEqualTo(1);
        assertThat(actualOwners.get(0)).isNotEqualTo(ownerSecond);
        assertThat(actualOwners.get(0)).isEqualTo(ownerFirst);
    }
    Owner getOwner(String username, long chatId) {
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .build();
        return ownerService.createOwner(owner);
    }
    Cat getCat(String name, LocalDate dateAdoption) {
        Cat cat = Cat.builder()
                .name(name)
                .dateAdoption(dateAdoption)
                .testPeriod(30)
                .adopted(false)
                .build();
        return catService.createCat(cat);
    }
    Dog getDog(String name, LocalDate dateAdoption) {
        Dog dog = Dog.builder()
                .name(name)
                .dateAdoption(dateAdoption)
                .testPeriod(30)
                .adopted(false)
                .build();
        return dogService.createDog(dog);
    }
}















