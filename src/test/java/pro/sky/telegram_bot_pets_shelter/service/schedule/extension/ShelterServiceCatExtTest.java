package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
                messageUtils, telegramBot, blackListService);
    }

    @Test
    void checkEndTestPeriodPetAndSendMessageAttemptOneTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwnerAndCatAndaReports(100L, currentDate, "Igor",
                "mur", 30, 1);
        Owner oleg = getOwnerAndCatAndaReports(200L, currentDate, "Oleg",
                "kis", 29, 1);

        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(message);

        shelterServiceCatExt.checkEndTestPeriodPetAndSendMessage();

        ArgumentCaptor<Long> argLong = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(2))
                .generationSendMessage(argLong.capture(), argString.capture());

        List<String> actualTexts = argString.getAllValues();
        String actualTextFirst = actualTexts.get(0);
        String actualTextSecond = actualTexts.get(1);
        assertThat(actualTextFirst).startsWith("Поздравляем");
        assertThat(actualTextSecond).startsWith("Вам назначен");

        List<Long> actualChatId = argLong.getAllValues();
        long actualChatIdFirst = actualChatId.get(0);
        long actualChatIdSecond = actualChatId.get(1);
        assertThat(actualChatIdFirst).isEqualTo(100L);
        assertThat(actualChatIdSecond).isEqualTo(200L);

        Cat mur = catService.findCat(1L);
        Boolean actualAdoptedMur = mur.getAdopted();
        assertThat(actualAdoptedMur).isTrue();

        Cat kis = catService.findCat(2L);
        Boolean actualAdoptedKis = kis.getAdopted();
        assertThat(actualAdoptedKis).isFalse();

        int actualAttemptKis = kis.getAttempt();
        assertThat(actualAttemptKis).isEqualTo(2);

        Integer actualTestPeriodKis = kis.getTestPeriod();
        assertThat(actualTestPeriodKis).isEqualTo(14);

        verify(telegramBot, times(2)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }

    @Test
    void checkEndTestPeriodPetAndSendMessageAttemptTwoTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwnerAndCatAndaReports(100L, currentDate, "Ivan",
                "myu", 30, 2);
        Owner oleg = getOwnerAndCatAndaReports(200L, currentDate, "Pety",
                "dusy", 29, 2);

        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(message);

        shelterServiceCatExt.checkEndTestPeriodPetAndSendMessage();

        ArgumentCaptor<Long> argLong = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(2))
                .generationSendMessage(argLong.capture(), argString.capture());

        List<String> actualTexts = argString.getAllValues();
        String actualTextFirst = actualTexts.get(0);
        String actualTextSecond = actualTexts.get(1);
        assertThat(actualTextFirst).startsWith("Поздравляем");
        assertThat(actualTextSecond).startsWith("Увы возвращайте питомца");

        List<Long> actualChatId = argLong.getAllValues();
        long actualChatIdFirst = actualChatId.get(0);
        long actualChatIdSecond = actualChatId.get(1);
        assertThat(actualChatIdFirst).isEqualTo(100L);
        assertThat(actualChatIdSecond).isEqualTo(200L);

        Cat myu = catService.findCat(1L);
        Boolean actualAdoptedMyu = myu.getAdopted();
        assertThat(actualAdoptedMyu).isTrue();

        Cat dusy = catService.findCat(2L);
        Boolean actualAdoptedDusy = dusy.getAdopted();
        assertThat(actualAdoptedDusy).isEqualTo(null);


        int actualAttemptDusy = dusy.getAttempt();
        assertThat(actualAttemptDusy).isEqualTo(1);

        Integer actualTestPeriodDusy = dusy.getTestPeriod();
        assertThat(actualTestPeriodDusy).isEqualTo(30);

        verify(telegramBot, times(2)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }


    private Owner getOwnerAndCatAndaReports(long chatId, LocalDate dateReport,
                                            String username, String name, int countDay, int attempt) {
        Set<Report> reports = getReports(dateReport, chatId, countDay);
        Cat cat = getCat(name, reports, attempt);
        return getOwner(username, cat, chatId);
    }


    private Report getReport(LocalDate dateReport, long chatId) {
        var report = Report.builder()
                .dateReport(dateReport)
                .chatId(chatId)
                .healthStatus("health")
                .fileId("12345")
                .build();
        return reportService.createReport(report);
    }

    private Set<Report> getReports(LocalDate dateReport, long chatId, int countDay) {
        Set<Report> reports = new HashSet<>();
        for (int i = 0; i < countDay; i++) {
            var report = getReport(dateReport.minusDays(i), chatId);
            reports.add(report);
        }
        return reports;
    }


    private Owner getOwner(String username, Cat cat, long chatId) {
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .build();
        Owner newOwner = ownerService.createOwner(owner);
        newOwner.setCat(cat);
        return ownerService.editOwner(owner);
    }

    private Cat getCat(String name, Set<Report> reports, int attempt) {
        currentDate = LocalDate.now();
        Cat cat = Cat.builder()
                .name(name)
                .dateAdoption(currentDate.minusDays(30))
                .testPeriod(30)
                .adopted(false)
                .attempt(attempt)
                .build();
        Cat newCat = catService.createCat(cat);
        newCat.setReport(reports);
        return catService.editCat(newCat);
    }
}