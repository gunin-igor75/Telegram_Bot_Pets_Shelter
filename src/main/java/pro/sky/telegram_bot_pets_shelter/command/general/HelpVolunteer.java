package pro.sky.telegram_bot_pets_shelter.command.general;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


/**
 * Данный класс формрует сообщения исодя из выбора volunteer
 */
@Component
public class HelpVolunteer implements Command {

    private final MessageUtils messageUtils;

    public HelpVolunteer(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        return messageUtils.sendMessageCallOwner();
    }
}
