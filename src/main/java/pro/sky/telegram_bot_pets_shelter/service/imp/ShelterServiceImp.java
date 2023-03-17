package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@EnableScheduling
public class ShelterServiceImp {
    private final OwnerService ownerService;
    private final ReportService reportService;
    private final MessageUtils messageUtils;
    private final TelegramBot telegramBot;
    private LocalDate currentDate;

    public ShelterServiceImp(OwnerService ownerService, ReportService reportService, MessageUtils messageUtils, TelegramBot telegramBot) {
        this.ownerService = ownerService;
        this.reportService = reportService;
        this.messageUtils = messageUtils;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 00 21 * * *")
    public void checkEndTestPeriodCat() {
        currentDate = LocalDate.now();
        List<Owner> ownersEndTestPeriod = ownerService.getOwnerCatsEndTestPeriod(currentDate);
        for (Owner owner : ownersEndTestPeriod) {
            int deltaClearAndBadReport = getDeltaClearAndBadReport(owner);
        }
    }

    private int getDeltaClearAndBadReport(Owner owner) {
        Cat cat = owner.getCat();
        long id = cat.getId();
        int testPeriod = cat.getTestPeriod();
        return testPeriod - reportService.getCountReportCatClear(id);
    }


}
