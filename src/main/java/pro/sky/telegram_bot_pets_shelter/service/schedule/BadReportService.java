package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class BadReportService {
    private final MessageUtils messageUtils;
    private final TelegramBot telegramBot;
    private final ReportService reportService;

    public BadReportService(MessageUtils messageUtils, TelegramBot telegramBot, ReportService reportService) {
        this.messageUtils = messageUtils;
        this.telegramBot = telegramBot;
        this.reportService = reportService;
    }

    @Scheduled(cron = "0 00 21 * * *")
    private void SendMessageBadReport() {
        LocalDate currentDate = LocalDate.now();
        List<Long> chatIdBadReport = reportService.getChatIdBadReport(currentDate);
        var text = "Дорогой усыновитель, мы заметили, что ты заполняешь" +
                " отчет не так подробно, как необходимо. Пожалуйста, подойди" +
                " ответственнее к этому занятию. В противном случае волонтеры" +
                " приюта будут обязаны самолично проверять условия содержания животного»";
        chatIdBadReport.forEach(chatId -> {
            var message = messageUtils.generationSendMessage(chatId, text);
            telegramBot.sendAnswerMessage(message);
        });
    }
}




















