package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.*;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BotServiceImp {
    private final CatService catService;
    private final MessageUtils messageUtils;
    private final TelegramBot telegramBot;
    private final OwnerService ownerService;
    private final BlackListService blackListService;
    private final VolunteerService volunteerService;
    private final TaskService taskService;
    private LocalDate currentDate;

    private Random random;
    public BotServiceImp(CatService catService, MessageUtils messageUtils,
                         TelegramBot telegramBot, OwnerService ownerService, BlackListService blackListService, VolunteerService volunteerService, TaskService taskService) {
        this.catService = catService;
        this.messageUtils = messageUtils;
        this.telegramBot = telegramBot;
        this.ownerService = ownerService;
        this.blackListService = blackListService;
        this.volunteerService = volunteerService;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 00 21 * * *")
    public void sendMessageEveryDayNoReports() {
        List<Long> badOwners = getChatIdBadOwners();
        String text = "Прошу отправить отчет о содержании питомца";
        badOwners.forEach(chatId -> {
            var message = messageUtils.generationSendMessage(chatId, text);
            telegramBot.sendAnswerMessage(message);
        });
    }

    private List<Long> getChatIdBadOwners() {
        List<Report> latestReports = getReportMaxDate();
        return getChatIdBadOwner(latestReports);
    }

    public List<Report> getReportMaxDate() {
        currentDate = LocalDate.now();
        List<Cat> catsAdopted = catService.getCatsByAdoptedIsFalse(currentDate);
        List<Report> reports = new ArrayList<>();
        for (Cat cat : catsAdopted) {
            Report report = cat.getReport()
                    .stream()
                    .max(Comparator.comparing(Report::getDateReport)).orElse(null);
            assert report != null;
            reports.add(report);
        }
        return reports;
    }

    public List<Long> getChatIdBadOwner(List<Report> reports) {
        return reports.stream()
                .filter(e -> e.getDateReport().isBefore(LocalDate.now()))
                .map(Report::getChatId)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 00 21 * * *")
    public void createBlackListOwnerTaskVolunteer() {
        List<Long> badOwners = getChatIMoreThanTwoDaysNoReports();
        createBlackListAndTask(badOwners);
    }

    private List<Long> getChatIMoreThanTwoDaysNoReports() {
        List<Report> reports = getReportMaxDate();
        currentDate = LocalDate.now();
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
        var index = random.nextInt(volunteers.size());
        return volunteers.get(index);
    }
}
