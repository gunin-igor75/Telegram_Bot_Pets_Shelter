package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NotReportsOneDayService {
    private final MessageUtils messageUtils;
    private final TelegramBot telegramBot;

    public NotReportsOneDayService(MessageUtils messageUtils, TelegramBot telegramBot) {
        this.messageUtils = messageUtils;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 00 21 * * *")
    private void sendMessageEveryDayNoReports() {
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

    private List<Long> getChatIdBadOwner(List<Report> reports) {
        return reports.stream()
                .filter(e -> e.getDateReport().isBefore(LocalDate.now()))
                .map(Report::getChatId)
                .collect(Collectors.toList());
    }

    protected abstract List<Report> getReportMaxDate();
}
