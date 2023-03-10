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
        long chatId = messageUtils.getChatId(update);
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        var persistentcat = persistentOwner.getCat();
        boolean checkCaption = messageUtils.checkReportString(caption);
        boolean checkText = messageUtils.checkReportString(text);
        Report transientReport = null;
        if (fileId == null && !checkText) {
            return messageUtils.generationSendMessage(update,
                    "send report or enter /cancel");
        }
        if (fileId != null && !checkText) {
            if (checkCaption) {
                transientReport = creteReport(chatId, fileId, caption);
                Report persistentReport = reportService.createReport(transientReport);
                persistentcat.setReport(persistentReport);
                catService.editCat(persistentcat);
                persistentOwner.setState(BASIC_STATE);
                ownerService.editOwner(persistentOwner);
                return messageUtils.generationSendMessage(update, "Все хорошо");
            } else {

            }
        } else {

        }
        return null;
    }

    private Report creteReport(long chatId, String fileId, String healthStatus) {
        return Report.builder()
                .chatId(chatId)
                .fileId(fileId)
                .healthStatus(healthStatus)
                .dateReport(LocalDate.now())
                .build();
    }

    private boolean checkReport(Report report) {
        return report.getFileId() != null && report.getHealthStatus() != null;
    }
}















