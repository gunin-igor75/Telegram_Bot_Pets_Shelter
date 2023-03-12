package pro.sky.telegram_bot_pets_shelter.command.cats.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@Component
@Slf4j
public class CatSaveReport implements Command {
    private final MessageUtils messageUtils;
    private final ReportService reportService;
    private final OwnerService ownerService;
    private final CatService catService;

    public CatSaveReport(MessageUtils messageUtils, ReportService reportService,
                         OwnerService ownerService, CatService catService) {
        this.messageUtils = messageUtils;
        this.reportService = reportService;
        this.ownerService = ownerService;
        this.catService = catService;
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
        Report persistentReport = reportService.findReportCat(chatId, dateReport);
        if (checkText && persistentReport == null) {
            creteReportCat(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send a photo");
        }
        if (fileId != null && !checkCaption && persistentReport == null) {
            creteReportCat(chatId, fileId, text);
            return messageUtils.generationSendMessage(update,
                    "send information in text");
        }
        if (persistentReport != null && fileId != null) {
            editReportCat(persistentReport, fileId);
            return messageUtils.generationSendMessage(update, "Thank you");
        }
        if (persistentReport != null && checkText) {
            editReportCat(persistentReport, text);
            return messageUtils.generationSendMessage(update, "Thank you");
        } else {
            return messageUtils.generationSendMessage(update, "Submit a report or enter /cansel");
        }
    }
    private void creteReportCat(long chatId, String fileId, String healthStatus) {
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        var persistentCat = persistentOwner.getCat();
        Report transientReport = Report.builder()
                .chatId(chatId)
                .fileId(fileId)
                .healthStatus(healthStatus)
                .dateReport(LocalDate.now())
                .build();
        Report persistentReport = reportService.createReport(transientReport);
        persistentCat.setReport(persistentReport);
        catService.editCat(persistentCat);
        persistentOwner.setState(BASIC_STATE);
        ownerService.editOwner(persistentOwner);
    }

    private void editReportCat(Report report, String fileIdOrHealthStatus) {
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















