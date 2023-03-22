package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.service.schedule.ShelterServicePet;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

@Service
@Slf4j
public class ShelterServiceCatExt extends ShelterServicePet {
    private final CatService catService;
    public ShelterServiceCatExt(OwnerService ownerService, ReportService reportService, CatService catService,
                                MessageUtils messageUtils, TelegramBot telegramBot, BlackListService blackListService) {
        super(ownerService, reportService, messageUtils, telegramBot, blackListService);
        this.catService = catService;
    }

    public int getAttempt(Owner owner) {
        Cat cat = owner.getCat();
        return cat.getAttempt();
    }

    public void endOfAdoptionPositive(Owner owner) {
        Cat cat = owner.getCat();
        cat.setAdopted(true);
    }


    public void endOfAdoptionNegative(Owner owner) {
        Cat cat = owner.getCat();
        cat.setTestPeriod(30);
        cat.setAdopted(null);
        cat.setDateAdoption(null);
        cat.setAttempt(1);
        catService.editCat(cat);
    }

    public int getNewTestPeriod(Owner owner) {
        BlackList ownerBlackList = blackListService.findBlackListByChatId(owner.getChatId());
        int testPeriod = ownerBlackList == null ? 14 : 30;
        currentDate = LocalDate.now();
        Cat cat = owner.getCat();
        cat.setTestPeriod(testPeriod);
        cat.setDateAdoption(currentDate);
        cat.setAttempt(2);
        catService.editCat(cat);
        return testPeriod;
    }

    public int getMissingAndBadReport(Owner owner) {
        Cat cat = owner.getCat();
        long id = cat.getId();
        int testPeriod = cat.getTestPeriod();
        return testPeriod - reportService.getCountReportCatClear(id);
    }
}
