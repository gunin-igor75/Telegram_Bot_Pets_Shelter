package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.DogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportServiceImpRepositoryTest {
    @Autowired
    private ReportServiceImpl reportService;
    @Autowired
    private CatServiceImpl catService;
    @Autowired
    private DogService dogService;


    @Test
    void findReportCat() {
        givenCatFirst();
        givenCatSecond();
        var dateSecond = LocalDate.of(2023, 3, 11);
        Report reportCat = reportService.findReportCat(100L, dateSecond);
        thenReportCatFind(dateSecond, reportCat);
    }



    private void givenCatFirst() {
        var dateAdopted = LocalDate.of(2023, 3, 9);
        var dateFirst = LocalDate.of(2023, 3, 10);
        var dateSecond = LocalDate.of(2023, 3, 11);
        var chatIdFirst = 100L;
        var reportFirst = Report.builder()
                .dateReport(dateFirst)
                .chatId(chatIdFirst)
                .fileId("shdgshdshdgshd")
                .healthStatus("diet")
                .build();
        var reportSecond = Report.builder()
                .dateReport(dateSecond)
                .fileId("fgfgfgfgfgfgffg")
                .healthStatus("health")
                .chatId(chatIdFirst)
                .build();
        Report persistentReportFirst = reportService.createReport(reportFirst);
        Report persistentReportSecond = reportService.createReport(reportSecond);
        Set<Report> reports = Set.of(persistentReportFirst, persistentReportSecond);
        Cat cat = createCat(dateAdopted, "Mur");
        cat.setReport(reports);
        catService.editCat(cat);
    }

    private void givenCatSecond() {
        var dateAdopted = LocalDate.of(2023, 3, 9);
        var dateFirst = LocalDate.of(2023, 3, 10);
        var chatIdFirst = 200L;
        var reportFirst = Report.builder()
                .dateReport(dateFirst)
                .chatId(chatIdFirst)
                .build();
        Report persistentReportFirst = reportService.createReport(reportFirst);
        Set<Report> reports = Set.of(persistentReportFirst);
        Cat cat = createCat(dateAdopted, "Tom");
        cat.setReport(reports);
        catService.editCat(cat);
    }

    private Cat createCat(LocalDate dateAdopted, String name) {
        Cat cat = Cat.builder()
                .adopted(false)
                .name(name)
                .testPeriod(30)
                .dateAdoption(dateAdopted)
                .build();
        return catService.createCat(cat);
    }

    private  void thenReportCatFind(LocalDate dateSecond, Report reportCat) {
        assertThat(reportCat.getDateReport()).isEqualTo(dateSecond);
        assertThat(reportCat.getChatId()).isEqualTo(100);
        assertThat(reportCat.getChatId()).isNotEqualTo(200);
    }

    @Test
    void findReportDog() {
        givenDogFirst();
        givenDogSecond();
        var dateSecond = LocalDate.of(2023, 3, 11);
        Report reportDog = reportService.findReportDog(100L, dateSecond);
        thenReportDogFind(dateSecond, reportDog);
    }

    private void givenDogFirst() {
        var dateAdopted = LocalDate.of(2023, 3, 9);
        var dateFirst = LocalDate.of(2023, 3, 10);
        var dateSecond = LocalDate.of(2023, 3, 11);
        var chatIdFirst = 100L;
        var reportFirst = Report.builder()
                .dateReport(dateFirst)
                .chatId(chatIdFirst)
                .fileId("shdgshdshdgshd")
                .healthStatus("diet")
                .build();
        var reportSecond = Report.builder()
                .dateReport(dateSecond)
                .fileId("fgfgfgfgfgfgffg")
                .healthStatus("health")
                .chatId(chatIdFirst)
                .build();
        Report persistentReportFirst = reportService.createReport(reportFirst);
        Report persistentReportSecond = reportService.createReport(reportSecond);
        Set<Report> reports = Set.of(persistentReportFirst, persistentReportSecond);
        Dog dog = createDog(dateAdopted, "Tim");
        dog.setReport(reports);
        dogService.editDog(dog);
    }

    private void givenDogSecond() {
        var dateAdopted = LocalDate.of(2023, 3, 9);
        var dateFirst = LocalDate.of(2023, 3, 10);
        var chatIdFirst = 200L;
        var reportFirst = Report.builder()
                .dateReport(dateFirst)
                .chatId(chatIdFirst)
                .build();
        Report persistentReportFirst = reportService.createReport(reportFirst);
        Set<Report> reports = Set.of(persistentReportFirst);
        Dog dog = createDog(dateAdopted, "Bim");
        dog.setReport(reports);
        dogService.editDog(dog);
    }

    private Dog createDog(LocalDate dateAdopted, String name) {
        Dog dog = Dog.builder()
                .adopted(false)
                .name(name)
                .testPeriod(30)
                .dateAdoption(dateAdopted)
                .build();
        return dogService.createDog(dog);
    }

    private  void thenReportDogFind(LocalDate dateSecond, Report reportDog) {
        assertThat(reportDog.getDateReport()).isEqualTo(dateSecond);
        assertThat(reportDog.getChatId()).isEqualTo(100);
        assertThat(reportDog.getChatId()).isNotEqualTo(200);
    }

    @Test
    void getCountReportCatClear() {
        givenCatFirst();
        givenCatSecond();
        int countReportCatClear = reportService.getCountReportCatClear(1);
        thenGetCountReportCatClear(countReportCatClear);
    }

    private void thenGetCountReportCatClear(int countReportCatClear) {
        assertThat(countReportCatClear).isEqualTo(2);
        assertThat(countReportCatClear).isNotEqualTo(1);
    }

    @Test
    void getCountReportDogClear() {
        givenDogFirst();
        givenDogSecond();
        int countReportDogClear = reportService.getCountReportDogClear(1);
        thenGetCountReportDogClear(countReportDogClear);
    }

    private void thenGetCountReportDogClear(int countReportDogClear) {
        assertThat(countReportDogClear).isEqualTo(2);
        assertThat(countReportDogClear).isNotEqualTo(1);
    }

    @Test
    void getChatIdBadReport() {
        List<Long> chatIdBadReportActual = givenReports();
        LocalDate currentDate = LocalDate.of(2023, 3, 11);
        List<Long> chatIdBadReportExpected = reportService.getChatIdBadReport(currentDate);
        thenChatIdBadReportEquals(chatIdBadReportExpected, chatIdBadReportActual);

    }

    private void thenChatIdBadReportEquals(List<Long> chatIdBadReportExpected,
                                           List<Long>  chatIdBadReportActual) {
        assertThat(chatIdBadReportExpected).containsExactlyInAnyOrderElementsOf(chatIdBadReportActual);
    }

    private List<Long> givenReports() {
        Long chatIdFirst = 100L;
        var reportFirst = Report.builder()
                .dateReport(LocalDate.of(2023, 3, 11))
                .chatId(chatIdFirst)
                .healthStatus("diet")
                .build();
        Long chatIdSecond = 200L;
        var reportSecond = Report.builder()
                .dateReport(LocalDate.of(2023, 3, 11))
                .fileId("fgfgfgfgfgfgffg")
                .chatId(chatIdSecond)
                .build();
        Long chatIdThird = 300L;
        var reportThird = Report.builder()
                .dateReport(LocalDate.of(2023,3,8))
                .chatId(chatIdThird)
                .fileId("123455")
                .healthStatus("diet")
                .build();
        Long chatIdFourth = 400L;
        var reportFourth = Report.builder()
                .dateReport(LocalDate.of(2023,3,7))
                .chatId(chatIdFourth)
                .fileId("123455cscsc")
                .build();
        reportService.createReport(reportFirst);
        reportService.createReport(reportSecond);
        reportService.createReport(reportThird);
        reportService.createReport(reportFourth);
        return List.of(100L, 200L);
    }
}