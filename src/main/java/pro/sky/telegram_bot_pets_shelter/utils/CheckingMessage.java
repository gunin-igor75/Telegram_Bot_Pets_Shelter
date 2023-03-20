package pro.sky.telegram_bot_pets_shelter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.CommandStorage;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;

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
    private final OwnerService ownerService;

    public CheckingMessage(CommandStorage commandStorage,
                           MessageUtils messageUtils, OwnerService ownerService) {
        this.commandStorage = commandStorage;
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    public SendMessage checkUpdate(Update update) {
        var chatId = messageUtils.getChatId(update);
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        if (update.hasMessage() && update.getMessage().hasText() &&
                ("/start".equals(update.getMessage().getText()) ||
                        "/cancel".equals(update.getMessage().getText()))) {
            return getSendMessageStartOrCancel(update, persistentOwner);
        } else if (persistentOwner == null) {
            return commandStorage
                    .getStorage()
                    .get("startInfo")
                    .execute(update);
        } else if (persistentOwner.getState() == BASIC_STATE) {
            return checkUpdateBasicState(update);
        } else if (persistentOwner.getState() == REPORT_CATS_STATE) {
            return commandStorage
                    .getStorage()
                    .get("catSaveReport")
                    .execute(update);
        } else if (persistentOwner.getState() == REPORT_DOGS_STATE) {
            return commandStorage
                    .getStorage()
                    .get("dogSaveReport")
                    .execute(update);
        } else {
            return commandStorage
                    .getStorage()
                    .get("helpVolunteer")
                    .execute(update);
        }
    }

    private SendMessage getSendMessageStartOrCancel(Update update, Owner owner) {
        String command;
        if (update.getMessage().getText().equals("/start") && owner != null &&
                owner.getLastAction() != null) {
            command = owner.getLastAction();
        } else {
            command = update
                    .getMessage()
                    .getText()
                    .split("\\s+")[0]
                    .substring(1);
        }
        return commandStorage
                .getStorage()
                .get(command)
                .execute(update);
    }

    private SendMessage checkUpdateBasicState(Update update) {
        String command;
        if (update.hasMessage() && update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(PREFIX)) {
            command = update
                    .getMessage()
                    .getText()
                    .split("\\s+")[0]
                    .substring(1);
            return commandStorage
                    .getStorage()
                    .get(command)
                    .execute(update);
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
            command = update.getCallbackQuery().getData();
            if (commandStorage.getStorage().containsKey(command)) {
                return commandStorage
                        .getStorage()
                        .get(command)
                        .execute(update);
            } else if (Character.isDigit(command.charAt(0))) {
                return commandStorage
                        .getStorage()
                        .get(command.split("\\s+")[1])
                        .execute(update);
            }
        } else if (update.getMessage().hasContact()) {
            return commandStorage.getStorage().get("saveContacts").execute(update);
        }
        return commandStorage
                .getStorage()
                .get("helpVolunteer")
                .execute(update);
    }
}
