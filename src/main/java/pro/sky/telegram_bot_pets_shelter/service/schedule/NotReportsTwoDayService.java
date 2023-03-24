package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegram_bot_pets_shelter.entity.*;
import pro.sky.telegram_bot_pets_shelter.service.BlackListService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.TaskService;
import pro.sky.telegram_bot_pets_shelter.service.VolunteerService;

import java.time.LocalDate;

import java.util.List;
import java.util.Random;

public abstract class NotReportsTwoDayService {
    private final OwnerService ownerService;
    private final BlackListService blackListService;
    private final VolunteerService volunteerService;
    private final TaskService taskService;

    public NotReportsTwoDayService(OwnerService ownerService, BlackListService blackListService,
                                   VolunteerService volunteerService, TaskService taskService) {
        this.ownerService = ownerService;
        this.blackListService = blackListService;
        this.volunteerService = volunteerService;
        this.taskService = taskService;
    }

    protected abstract List<Report> getReportMaxDate();

    @Scheduled(cron = "0 00 21 * * *")
    protected void createBlackListOwnerTaskVolunteer() {
        List<Long> badOwners = getChatIdMoreThanTwoDaysNoReports();
        createBlackListAndTask(badOwners);
    }

    private List<Long> getChatIdMoreThanTwoDaysNoReports() {
        List<Report> reports = getReportMaxDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate borderDate = currentDate.minusDays(2);
        return reports.stream()
                .filter(report -> report.getDateReport().isBefore(borderDate))
                .map(Report::getChatId)
                .toList();
    }

    private void createBlackListAndTask(List<Long> badOwners) {
        badOwners.forEach(chatId -> {
            Owner owner = ownerService.findOwnerByChatId(chatId);
            BlackList blackList = BlackList.builder()
                    .username(owner.getUsername())
                    .chatId(chatId)
                    .build();
            BlackList transientBlackList = blackListService.createBlackList(blackList);
            Volunteer volunteer = getRandomVolunteer();
            Task task = Task.builder()
                    .done(false)
                    .blackList(transientBlackList)
                    .volunteer(volunteer)
                    .build();
            taskService.createTask(task);
        });
    }

    private Volunteer getRandomVolunteer() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        Random random = new Random();
        var index = random.nextInt(volunteers.size());
        return volunteers.get(index);
    }
}
