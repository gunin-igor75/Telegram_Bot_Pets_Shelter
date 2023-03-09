package pro.sky.telegram_bot_pets_shelter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.CommandStorage;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.service.VisitorService;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.*;

@Component
@Slf4j
public class CheckingMessage {
    /**
     * Пефикс входящего сообщения
     */
    private final String PREFIX = "/";
    /**
     * бин, содержащий ассоциативный массив, где
     * ключ - название бина(класса отвечающий за действие в ответ на команду бота)
     * значение - сам бин
     */
    private final CommandStorage commandStorage;
    private final MessageUtils messageUtils;
    private final VisitorService visitorService;
    private final OwnerService ownerService;

    private final ReportService reportService;

    public CheckingMessage(CommandStorage commandStorage, MessageUtils messageUtils,
                           VisitorService visitorService, OwnerService ownerService,
                           ReportService reportService) {
        this.commandStorage = commandStorage;
        this.messageUtils = messageUtils;
        this.visitorService = visitorService;
        this.ownerService = ownerService;
        this.reportService = reportService;
    }

    public SendMessage checkUpdate(Update update) {
        var chatId = messageUtils.getChatId(update);
        var persistentVisitor = visitorService.findVisitorByChatId(chatId);
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        if (update.hasMessage() && update.getMessage().hasText() &&
                ("/start".equals(update.getMessage().getText()) ||
                        "/cansel".equals(update.getMessage().getText()))) {
            return commandStorage
                    .getStorage()
                    .get(update
                            .getMessage()
                            .getText()
                            .split("\\s+")[0]
                            .substring(1))
                    .execute(update);
        } else if (persistentVisitor == null && persistentOwner == null) {
            return commandStorage.getStorage().get("startInfo").execute(update);
        } else {
            assert persistentVisitor != null;
            if (persistentVisitor.getState() == BASIC_STATE ||
                    persistentOwner.getState() == BASIC_STATE) {
                return checkUpdateBasicState(update);
            } else if (persistentOwner.getState() == REPORT_CATS_STATE) {
                return checkUpdateReportCatsState(update);
            } else if (persistentOwner.getState() == REPORT_DOGS_STATE) {
                return checkUpdateReportDogsState(update);
            } else {
                log.error("Что то пошло не так есть неизвестная команда или условие");
                return commandStorage.getStorage().get("helpVolunteer").execute(update);
            }
        }
    }

    private SendMessage checkUpdateBasicState(Update update) {
        String key;
        if (update.hasMessage() && update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(PREFIX)) {
            key = update.getMessage().getText().split("\\s+")[0].substring(1);
            return commandStorage.getStorage().get(key).execute(update);
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
            key = update.getCallbackQuery().getData();
            if (commandStorage.getStorage().containsKey(key)) {
                return commandStorage.getStorage().get(key).execute(update);
            } else if (Character.isDigit(key.charAt(0))) {
                return commandStorage.getStorage().get(key.split("\\s+")[1]).execute(update);
            }
        }
        return commandStorage.getStorage().get("helpVolunteer").execute(update);
    }

    private SendMessage checkUpdateReportCatsState(Update update) {
        var fileId = update.getMessage().getPhoto().get(0).getFileId();
        var caption = update.getMessage().getCaption();
        var text = update.getMessage().getText();
        Report report = null;
        if (fileId == null  && checkReportString(text)) {
            return messageUtils.generationSendMessage(update,
                    "send report or enter /cancel");
        } else if (fileId == null) {
            report = Report.builder()
                    .healthStatus(text)
                    .dateReport(LocalDate.now())
                    .build();
            reportService.createReport(report);
        } else if (!checkReportString(caption)) {
            report = Report.builder()
                    .dateReport(LocalDate.now())
                    .fileId(fileId)
                    .build();
            reportService.createReport(report);
        } else {
            report = Report.builder()
                    .dateReport(LocalDate.now())
                    .healthStatus(caption)
                    .fileId(fileId)
                    .build();
            reportService.createReport(report);
        }

        return null;
    }

    private boolean checkReportString(String string) {
        return string != null
                && (string.contains("diet")
                || string.contains("health")
                || string.contains("behavior"));
    }

    private SendMessage checkUpdateReportDogsState(Update update) {
        return null;
    }
}
