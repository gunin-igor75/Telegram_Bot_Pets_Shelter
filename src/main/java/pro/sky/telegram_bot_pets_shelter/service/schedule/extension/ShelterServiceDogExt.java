package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.service.schedule.ShelterServicePet;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ShelterServiceDogExt extends ShelterServicePet {
    private final DogService dogService;

    public ShelterServiceDogExt(OwnerService ownerService, ReportService reportService, DogService dogService,
                                MessageUtils messageUtils, TelegramBot telegramBot, BlackListService blackListService) {
        super(ownerService, reportService, messageUtils, telegramBot, blackListService);
        this.dogService = dogService;
    }

    @Override
    protected List<Owner> getOwnersEndTestPeriod() {
        currentDate = LocalDate.now();
        return ownerService.getOwnerDogsEndTestPeriod(currentDate);
    }

    public int getMissingAndBadReport(Owner owner) {
        Dog dog = getPersistentDog(owner);
        long id = dog.getId();
        int testPeriod = dog.getTestPeriod();
        int countReportDogClear = reportService.getCountReportDogClear(id);
        return testPeriod - countReportDogClear;
    }

    public int getAttempt(Owner owner) {
        Dog dog = getPersistentDog(owner);
        return dog.getAttempt();
    }

    public void endOfAdoptionPositive(Owner owner) {
        Dog dog = getPersistentDog(owner);
        dog.setAdopted(true);
        dogService.editDog(dog);
    }

    public void endOfAdoptionNegative(Owner owner) {
        Dog dog = getPersistentDog(owner);
        dog.setTestPeriod(30);
        dog.setAdopted(null);
        dog.setDateAdoption(null);
        dog.setAttempt(1);
        dogService.editDog(dog);
    }

    public int getNewTestPeriod(Owner owner) {
        BlackList ownerBlackList = blackListService.findBlackListByChatId(owner.getChatId());
        int testPeriod = ownerBlackList == null ? 14 : 30;
        currentDate = LocalDate.now();
        Dog dog = getPersistentDog(owner);
        dog.setTestPeriod(testPeriod);
        dog.setDateAdoption(currentDate);
        dog.setAttempt(2);
        dogService.editDog(dog);
        return testPeriod;
    }



    public Dog getPersistentDog(Owner owner) {
        Dog dog = owner.getDog();
        Long id = dog.getId();
        return dogService.findDog(id);
    }
}
