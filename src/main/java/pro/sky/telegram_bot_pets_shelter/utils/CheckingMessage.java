package pro.sky.telegram_bot_pets_shelter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.CommandStorage;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.VisitorService;

import java.util.Objects;

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
    private SendMessage message;

    public CheckingMessage(CommandStorage commandStorage, MessageUtils messageUtils,
                           VisitorService visitorService, OwnerService ownerService) {
        this.commandStorage = commandStorage;
        this.messageUtils = messageUtils;
        this.visitorService = visitorService;
        this.ownerService = ownerService;
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
        } else if (persistentOwner.getState() == BASIC_STATE ||
                Objects.requireNonNull(persistentVisitor).getState() == BASIC_STATE) {
            return checkUpdateBasicState(update);
        } else if (persistentOwner.getState() == REPORT_CATS_STATE) {
            return checkUpdateReportCatsState(update);
        } else if (persistentOwner.getState() == REPORT_DOGS_STATE) {
            return checkUpdateReportDogsState(update);
        } else {
            log.error("Что тщ пошло не так есть неизвестная команда или условие");
            return commandStorage.getStorage().get("helpVolunteer").execute(update);
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
        var photo = update.getMessage().getPhoto();
        var caption = update.getMessage().getCaption();
        if (photo == null && caption == null) {
            return messageUtils.generationSendMessage(update,
                    "send report or enter /cancel");
        } else if (photo == null && checkReport(caption)) {

        }

        return null;
    }

    private boolean checkReport(String caption) {
        return true;
    }

    private SendMessage checkUpdateReportDogsState(Update update) {
        return null;
    }
}
