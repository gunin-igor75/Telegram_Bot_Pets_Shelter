package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DogSaveReportTest {

    @Mock
    private MessageUtils messageUtils;

    @Autowired
    private ReportService reportService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private DogService dogService;

    private DogSaveReport dogSaveReport;

    private LocalDate currentDate;

    @BeforeEach
    void init() {
        dogSaveReport = new DogSaveReport(messageUtils,reportService,ownerService, dogService);
    }

    @Test
    void executeTest() {
        Update update = getUpdateMessCaptionAndPhotoTrue(null);
        givenDogAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessage();
        thenCheckReport();
        thenCheckOwner();
    }

    private void thenCheckOwner() {
        Owner actualOwner = ownerService.findOwnerByChatId(100L);
        assertThat(actualOwner.getState()).isEqualTo(BASIC_STATE);
    }

    private void thenCheckSendMessage() {
        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Спасибо");
    }

    private void thenCheckReport() {
        Dog dog = dogService.findDog(1L);
        List<Report> reports = List.copyOf(dog.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getFileId()).isEqualTo("1234567");
        assertThat(actualReport.getHealthStatus()).isEqualTo("Диета");
    }

    @Test
    void executeTextTrueTest() {
        Update update = getUpdateMessTextTrue("Диета");
        givenDogAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessageTextTrue();
        thenCheckReportTextTrue();
        thenCheckOwner();
    }


    private void thenCheckSendMessageTextTrue() {
        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Отправить фото");
    }

    private void thenCheckReportTextTrue() {
        Dog dog = dogService.findDog(1L);
        List<Report> reports = List.copyOf(dog.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getHealthStatus()).isEqualTo("Диета");
    }



    @Test
    void executeTestPhotoTrue() {
        Update update = getUpdateMessPhotoTrue(null);
        givenDogAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessagePhotoTrue();
        thenCheckReportPhotoTrue();
        thenCheckOwner();
    }


    private void thenCheckSendMessagePhotoTrue() {
        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Отправить информацию текстом");
    }

    private void thenCheckReportPhotoTrue() {
        Dog dog = dogService.findDog(1L);
        List<Report> reports = List.copyOf(dog.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getFileId()).isEqualTo("1234567");
    }



    @Test
    void executePhotoAndTextFalseTest() {
        Update update = getUpdateMessPhotoAndTextFalse(null);
        givenDogAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessagePhotoAndTextFalse();
        thenCheckOwnerPhotoAndTextFalse();
    }

    private void thenCheckOwnerPhotoAndTextFalse() {
        Owner actualOwner = ownerService.findOwnerByChatId(100L);
        assertThat(actualOwner.getState()).isEqualTo(null);
    }

    private void thenCheckSendMessagePhotoAndTextFalse() {
        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Отправьте отчет или выберите");
    }



    @Test
    void executeEditReportPhotoTrueTest() {
        Update update = getUpdateMessTextTrue("Диета");
        givenDogAndOwnerAndReportPhotoTrue();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessage();
        thenCheckReport();
        thenCheckOwner();
    }


    @Test
    void executeEditReportTextTrueTest() {
        Update update = getUpdateMessPhotoTrue(null);
        givenDogAndOwnerAndReportTextTrue();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        dogSaveReport.execute(update);

        thenCheckSendMessage();
        thenCheckReport();
        thenCheckOwner();
    }

    private void givenDogAndOwner() {
        Dog bim = createDog("bim");
        Owner igor = creteOwner("Igor", 100L);
        igor.setDog(bim);
        ownerService.editOwner(igor);
    }

    private Dog createDog(String name) {
        currentDate = LocalDate.now();
        Dog dog = Dog.builder()
                .dateAdoption(currentDate.minusDays(1))
                .adopted(false)
                .attempt(1)
                .testPeriod(30)
                .name(name)
                .build();
        return dogService.createDog(dog);
    }

    private void givenDogAndOwnerAndReportPhotoTrue() {
        Set<Report> reportsPhoto = createReportsPhoto("1234567");
        Dog bim = createDogAndReports("bim", reportsPhoto);
        Owner igor = creteOwner("Igor", 100L);
        igor.setDog(bim);
        ownerService.editOwner(igor);
    }

    private void givenDogAndOwnerAndReportTextTrue() {
        Set<Report> reportsText = createReportsText("Диета");
        Dog bim = createDogAndReports("bim", reportsText);
        Owner igor = creteOwner("Igor", 100L);
        igor.setDog(bim);
        ownerService.editOwner(igor);
    }

    private Dog createDogAndReports(String name, Set<Report> reports) {
        currentDate = LocalDate.now();
        Dog dog = Dog.builder()
                .dateAdoption(currentDate.minusDays(1))
                .adopted(false)
                .attempt(1)
                .testPeriod(30)
                .name(name)
                .report(reports)
                .build();
        return dogService.createDog(dog);
    }

    private Owner creteOwner(String username, long chatId) {
        currentDate = LocalDate.now();
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .dateRegistration(currentDate.minusDays(1))
                .registration(true)
                .build();
        return ownerService.createOwner(owner);
    }

    private Set<Report> createReportsPhoto(String param) {
        return Set.of(createReport(param, null));
    }

    private Set<Report> createReportsText(String param) {
        return Set.of(createReport(null, param));
    }

    private Report createReport(String filId, String healthStatus) {
        currentDate = LocalDate.now();
        Report report = Report.builder()
                .dateReport(currentDate)
                .chatId(100L)
                .fileId(filId)
                .healthStatus(healthStatus)
                .build();
        return report;
    }

    private Update getUpdateMessCaptionAndPhotoTrue(String text) {
        Update update = new Update();
        Message message = new Message();
        PhotoSize photoSize = new PhotoSize();
        photoSize.setFileId("1234567");
        List<PhotoSize> photoSizes = List.of(photoSize);
        message.setCaption("Диета");
        message.setPhoto(photoSizes);
        User user = new User();
        user.setUserName("Igor");
        user.setId(100L);
        message.setFrom(user);
        message.setText(text);
        update.setMessage(message);
        return update;
    }

    private Update getUpdateMessTextTrue(String text) {
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        user.setUserName("Igor");
        user.setId(100L);
        message.setFrom(user);
        message.setText(text);
        update.setMessage(message);
        return update;
    }

    private Update getUpdateMessPhotoTrue(String text) {
        Update update = new Update();
        Message message = new Message();
        PhotoSize photoSize = new PhotoSize();
        photoSize.setFileId("1234567");
        List<PhotoSize> photoSizes = List.of(photoSize);
        message.setPhoto(photoSizes);
        User user = new User();
        user.setUserName("Igor");
        user.setId(100L);
        message.setFrom(user);
        message.setText(text);
        update.setMessage(message);
        return update;
    }

    private Update getUpdateMessPhotoAndTextFalse(String text) {
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        user.setUserName("Igor");
        user.setId(100L);
        message.setFrom(user);
        message.setText(text);
        update.setMessage(message);
        return update;
    }

}