package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegram_bot_pets_shelter.entity.*;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.service.schedule.extension.NotReportsTwoDayServiceCatsExt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class NotReportsTwoDayServiceTest {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private CatService catService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NotReportsTwoDayServiceCatsExt notReportsTwoDayService;

    private LocalDate currentDate;

    @Test
    void createBlackListOwnerTaskVolunteerTest() {
        getCatAndReportsAndOwners();
        getVolunteers();
        notReportsTwoDayService.createBlackListOwnerTaskVolunteer();
        thenCreateBlackListOwnerTaskVolunteer();

    }

    private void thenCreateBlackListOwnerTaskVolunteer() {
        List<BlackList> actualBlackLists = blackListService.getAllBlackLists();
        List<Task> actualTasks = taskService.getAllTasks();
        assertThat(actualBlackLists.size()).isEqualTo(2);
        assertThat(actualTasks.size()).isEqualTo(2);
        List<String> nameOwners = getNameOwner(actualBlackLists);
        assertThat(nameOwners).containsExactlyInAnyOrderElementsOf(List.of("Bob", "Curt"));
        List<Long> chatIdOwners = getChatIdOwner(actualBlackLists);
        assertThat(chatIdOwners).containsExactlyInAnyOrderElementsOf(List.of(100L, 200L));

    }

    private List<String> getNameOwner(List<BlackList> blackLists) {
        List<String> names = new ArrayList<>();
        blackLists.forEach(e -> names.add(e.getUsername()));
        return names;
    }

    private List<Long> getChatIdOwner(List<BlackList> blackLists) {
        List<Long> names = new ArrayList<>();
        blackLists.forEach(e -> names.add(e.getChatId()));
        return names;
    }

    private void getCatAndReportsAndOwners() {
        currentDate = LocalDate.now();
        Report reportFirst = getReport(currentDate.minusDays(4), 100L);
        Report reportSecond = getReport(currentDate.minusDays(3), 200L);
        Report reportThird = getReport(currentDate.minusDays(1), 300L);
        Cat mur = getCat("mur", Set.of(reportFirst));
        Cat kisa = getCat("kisa", Set.of(reportSecond));
        Cat tom = getCat("tom", Set.of(reportThird));
        Owner bob = getOwner("Bob", 100L);
        Owner curt = getOwner("Curt", 200L);
        Owner sony = getOwner("Sony", 300L);
        bob.setCat(mur);
        curt.setCat(kisa);
        sony.setCat(tom);
        ownerService.editOwner(bob);
        ownerService.editOwner(curt);
        ownerService.editOwner(sony);
    }

    private Report getReport(LocalDate dateReport, long chatId) {
        return Report.builder()
                .dateReport(dateReport)
                .chatId(chatId)
                .build();
    }

    private void getVolunteers() {
        Volunteer igor = getVolunteer("Igor", 1L);
        volunteerService.createVolunteer(igor);
        Volunteer ani = getVolunteer("Ани", 2L);
        volunteerService.createVolunteer(ani);
    }

    private Volunteer getVolunteer(String username, long chatId) {
        Volunteer volunteer = Volunteer.builder()
                .username(username)
                .chatId(chatId)
                .build();
        return volunteerService.createVolunteer(volunteer);
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