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
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShelterServiceDogExtTest {
    @Mock
    private MessageUtils messageUtils;

    @Mock
    private TelegramBot telegramBot;

    @Autowired
    private DogService dogService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private BlackListService blackListService;

    private ShelterServiceDogExt shelterServiceDogExt;

    private LocalDate currentDate;

    @BeforeEach
    void init() {
        shelterServiceDogExt = new ShelterServiceDogExt(ownerService, reportService, dogService,
                messageUtils, telegramBot, blackListService);
    }

    @Test
    void checkEndTestPeriodPetAndSendMessageAttemptOneTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwnerAndDogAndaReports(100L, currentDate, "Igor",
                "bim", 30, 1);
        Owner oleg = getOwnerAndDogAndaReports(200L, currentDate, "Oleg",
                "bam", 29, 1);

        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(message);

        shelterServiceDogExt.checkEndTestPeriodPetAndSendMessage();

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

        Dog bim = dogService.findDog(1L);
        Boolean actualAdoptedBim = bim.getAdopted();
        assertThat(actualAdoptedBim).isTrue();

        Dog bam = dogService.findDog(2L);
        Boolean actualAdoptedBam = bam.getAdopted();
        assertThat(actualAdoptedBam).isFalse();

        int actualAttemptBam = bam.getAttempt();
        assertThat(actualAttemptBam).isEqualTo(2);

        Integer actualTestPeriodBam = bam.getTestPeriod();
        assertThat(actualTestPeriodBam).isEqualTo(14);

        verify(telegramBot, times(2)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }

    @Test
    void checkEndTestPeriodPetAndSendMessageAttemptTwoTest() {
        currentDate = LocalDate.now();
        Owner igor = getOwnerAndDogAndaReports(100L, currentDate, "Ivan",
                "tom", 30, 2);
        Owner oleg = getOwnerAndDogAndaReports(200L, currentDate, "Pety",
                "pop", 29, 2);

        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(message);

        shelterServiceDogExt.checkEndTestPeriodPetAndSendMessage();

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

        Dog tom = dogService.findDog(1L);
        Boolean actualAdoptedTom = tom.getAdopted();
        assertThat(actualAdoptedTom).isTrue();

        Dog pop = dogService.findDog(2L);
        Boolean actualAdoptedPop = pop.getAdopted();
        assertThat(actualAdoptedPop).isEqualTo(null);


        int actualAttemptPop = pop.getAttempt();
        assertThat(actualAttemptPop).isEqualTo(1);

        Integer actualTestPeriodPop = pop.getTestPeriod();
        assertThat(actualTestPeriodPop).isEqualTo(30);

        verify(telegramBot, times(2)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }


    private Owner getOwnerAndDogAndaReports(long chatId, LocalDate dateReport,
                                            String username, String name, int countDay, int attempt) {
        Set<Report> reports = getReports(dateReport, chatId, countDay);
        Dog dog = getDog(name, reports, attempt);
        return getOwner(username, dog, chatId);
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


    private Owner getOwner(String username, Dog dog, long chatId) {
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .build();
        Owner newOwner = ownerService.createOwner(owner);
        newOwner.setDog(dog);
        return ownerService.editOwner(owner);
    }

    private Dog getDog(String name, Set<Report> reports, int attempt) {
        currentDate = LocalDate.now();
        Dog dog = Dog.builder()
                .name(name)
                .dateAdoption(currentDate.minusDays(30))
                .testPeriod(30)
                .adopted(false)
                .attempt(attempt)
                .build();
        Dog newDog = dogService.createDog(dog);
        newDog.setReport(reports);
        return dogService.editDog(newDog);
    }

}