package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

@Service
@Slf4j
@EnableScheduling
public class ShelterServiceDogImp extends ShelterServicePet {
    private final DogService dogService;

    public ShelterServiceDogImp(OwnerService ownerService, ReportService reportService, DogService dogService,
                                MessageUtils messageUtils, TelegramBot telegramBot, BlackListService blackListService) {
        super(ownerService, reportService, messageUtils, telegramBot, blackListService);
        this.dogService = dogService;
    }

    public int getAttempt(Owner owner) {
        Dog dog = owner.getDog();
        return dog.getAttempt();
    }

    public void endOfAdoptionPositive(Owner owner) {
        Dog dog = owner.getDog();
        dog.setAdopted(true);
    }

    public void endOfAdoptionNegative(Owner owner) {
        Dog dog = owner.getDog();
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
        Dog dog = owner.getDog();
        dog.setTestPeriod(testPeriod);
        dog.setDateAdoption(currentDate);
        dog.setAttempt(2);
        dogService.editDog(dog);
        return testPeriod;
    }

    public int getMissingAndBadReport(Owner owner) {
        Dog dog = owner.getDog();
        long id = dog.getId();
        int testPeriod = dog.getTestPeriod();
        return testPeriod - reportService.getCountReportDogClear(id);
    }
}
