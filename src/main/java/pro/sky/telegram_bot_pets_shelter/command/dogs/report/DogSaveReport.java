package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@Component
@Slf4j
public class DogSaveReport implements Command {
    private final MessageUtils messageUtils;
    private final ReportService reportService;
    private final OwnerService ownerService;

    public DogSaveReport(MessageUtils messageUtils, ReportService reportService,
                         OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.reportService = reportService;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var fileId = update.getMessage().getPhoto().get(0).getFileId();
        var caption = update.getMessage().getCaption();
        var text = update.getMessage().getText();
        boolean checkCaption = messageUtils.checkReportString(caption);
        boolean checkText = messageUtils.checkReportString(text);
        var chatId = messageUtils.getChatId(update);
        LocalDate dateReport = LocalDate.now();
        Report persistentReport = reportService.findReportDog(chatId, dateReport);
        if (checkText && persistentReport == null) {
            creteReportDog(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send a photo");
        }
        if (fileId != null && !checkCaption && persistentReport == null) {
            creteReportDog(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send information in text");
        }
        if (persistentReport != null && fileId != null) {
            editReportDog(persistentReport, fileId);
            return messageUtils.generationSendMessage(update, "Thank you");
        }
        if (persistentReport != null && checkText) {
            editReportDog(persistentReport, text);
            return messageUtils.generationSendMessage(update, "Thank you");
        } else {
            return messageUtils.generationSendMessage(update, "Submit a report or enter /cansel");
        }
    }
    private void creteReportDog(long chatId, String fileId, String healthStatus) {
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        var dog = persistentOwner.getDog();
        Report transientReport = Report.builder()
                .chatId(chatId)
                .fileId(fileId)
                .healthStatus(healthStatus)
                .dateReport(LocalDate.now())
                .dog(dog)
                .build();
        reportService.createReport(transientReport);
        persistentOwner.setState(BASIC_STATE);
        ownerService.editOwner(persistentOwner);
    }

    private void editReportDog(Report report, String fileIdOrHealthStatus) {
        var persistentOwner = ownerService.findOwnerByChatId(report.getChatId());
        if (report.getFileId() == null) {
            report.setFileId(fileIdOrHealthStatus);
        } else {
            report.setHealthStatus(fileIdOrHealthStatus);
        }
        reportService.editReport(report);
        persistentOwner.setState(BASIC_STATE);
        ownerService.editOwner(persistentOwner);
    }
}
