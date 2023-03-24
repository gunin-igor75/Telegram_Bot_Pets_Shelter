package pro.sky.telegram_bot_pets_shelter.command.general;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


/**
 * Данный класс формирует сообщения исходя из выбора start:
 * Сохраняет в базу данных visitor
 */
@Component
public class Start implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;


    public Start(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var telegramUser = update.getMessage().getFrom();
        Owner persistentOwner = ownerService.findOrSaveOwner(telegramUser);
        return messageUtils.generationSendMessage(update, "Здравствуйте " + persistentOwner.getUsername() +
                " Вас приветствует бот приюта!");
    }
}