package pro.sky.telegram_bot_pets_shelter.command.general;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.imp.BotServiceImp;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


/**
 * Данный класс формрует сообщения исодя из выбора start:
 * Сохраняет в ьазу данных visitor
 */
@Component
public class Start implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;
//    private final BotServiceImp botServiceImp;

    public Start(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
//        this.botServiceImp = botServiceImp;
    }

    @Override
    public SendMessage execute(Update update) {
        var telegramUser = update.getMessage().getFrom();
        Owner owner = ownerService.findOrSaveOwner(telegramUser);
//        System.out.println(botServiceImp.getReportMaxDate());
        return messageUtils.generationSendMessage(update, "Здравствуйте " + owner.getUsername() +
                    " Вас приветствует бот приюта!");
    }
}
