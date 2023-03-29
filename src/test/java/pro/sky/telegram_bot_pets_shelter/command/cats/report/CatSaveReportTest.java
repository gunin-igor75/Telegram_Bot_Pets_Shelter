package pro.sky.telegram_bot_pets_shelter.command.cats.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.objects.*;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CatSaveReportTest {

    @Mock
    private MessageUtils messageUtils;

    @Autowired
    private ReportService reportService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private CatService catService;

    private CatSaveReport catSaveReport;

    private LocalDate currentDate;

    @BeforeEach
    void init() {
        catSaveReport = new CatSaveReport(messageUtils,reportService,ownerService,catService);
    }

    @Test
    void executeTest() {
        Update update = getUpdateMessCaptionAndPhotoTrue(null);
        givenCatAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

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
        Cat cat = catService.findCat(1L);
        List<Report> reports = List.copyOf(cat.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getFileId()).isEqualTo("1234567");
        assertThat(actualReport.getHealthStatus()).isEqualTo("Диета");
    }

    @Test
    void executeTextTrueTest() {
        Update update = getUpdateMessTextTrue("Диета");
        givenCatAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

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
        Cat cat = catService.findCat(1L);
        List<Report> reports = List.copyOf(cat.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getHealthStatus()).isEqualTo("Диета");
    }



    @Test
    void executeTestPhotoTrue() {
        Update update = getUpdateMessPhotoTrue(null);
        givenCatAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

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
        Cat cat = catService.findCat(1L);
        List<Report> reports = List.copyOf(cat.getReport());
        assertThat(reports.size()).isEqualTo(1);
        assertThat(reports.get(0)).isNotNull();
        Report actualReport = reportService.findReport(1L);
        assertThat(actualReport.getFileId()).isEqualTo("1234567");
    }



    @Test
    void executePhotoAndTextFalseTest() {
        Update update = getUpdateMessPhotoAndTextFalse(null);
        givenCatAndOwner();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

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
        givenCatAndOwnerAndReportPhotoTrue();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

        thenCheckSendMessage();
        thenCheckReport();
        thenCheckOwner();
    }


    @Test
    void executeEditReportTextTrueTest() {
        Update update = getUpdateMessPhotoTrue(null);
        givenCatAndOwnerAndReportTextTrue();

        when(messageUtils.getChatId(update)).thenReturn(100L);

        catSaveReport.execute(update);

        thenCheckSendMessage();
        thenCheckReport();
        thenCheckOwner();
    }

    private void givenCatAndOwner() {
        Cat mur = createCat("mur");
        Owner igor = creteOwner("Igor", 100L);
        igor.setCat(mur);
        ownerService.editOwner(igor);
    }

    private Cat createCat(String name) {
        currentDate = LocalDate.now();
        Cat cat = Cat.builder()
                .dateAdoption(currentDate.minusDays(1))
                .adopted(false)
                .attempt(1)
                .testPeriod(30)
                .name(name)
                .build();
       return catService.createCat(cat);
    }

    private void givenCatAndOwnerAndReportPhotoTrue() {
        Set<Report> reportsPhoto = createReportsPhoto("1234567");
        Cat mur = createCatAndReports("mur", reportsPhoto);
        Owner igor = creteOwner("Igor", 100L);
        igor.setCat(mur);
        ownerService.editOwner(igor);
    }

    private void givenCatAndOwnerAndReportTextTrue() {
        Set<Report> reportsText = createReportsText("Диета");
        Cat mur = createCatAndReports("mur", reportsText);
        Owner igor = creteOwner("Igor", 100L);
        igor.setCat(mur);
        ownerService.editOwner(igor);
    }

    private Cat createCatAndReports(String name, Set<Report> reports) {
        currentDate = LocalDate.now();
        Cat cat = Cat.builder()
                .dateAdoption(currentDate.minusDays(1))
                .adopted(false)
                .attempt(1)
                .testPeriod(30)
                .name(name)
                .report(reports)
                .build();
        return catService.createCat(cat);
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