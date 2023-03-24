package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.BlackListService;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ShelterServiceCatExtTest {

    @Mock
    private MessageUtils messageUtils;

    @Mock
    private TelegramBot telegramBot;

    @Autowired
    private CatService catService;
    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private BlackListService blackListService;

    private ShelterServiceCatExt shelterServiceCatExt;

    private LocalDate currentDate;

    @BeforeEach
    void init() {
        shelterServiceCatExt = new ShelterServiceCatExt(ownerService, reportService, catService,
                messageUtils,telegramBot, blackListService);
    }

    @Test
    void checkEndTestPeriodPetAndSendMessageTest() {

    }


    private Report getReport(LocalDate dateReport, long chatId) {
        return Report.builder()
                .dateReport(dateReport)
                .chatId(chatId)
                .build();
    }



    private Owner getOwner(String username, long chatId) {
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .build();
        return ownerService.createOwner(owner);
    }

    private Cat getCat(String name, Set<Report> reports) {
        currentDate = LocalDate.now();
        Cat cat = Cat.builder()
                .name(name)
                .dateAdoption(currentDate.minusDays(10))
                .testPeriod(30)
                .adopted(false)
                .report(reports)
                .build();
        return catService.createCat(cat);
    }

}