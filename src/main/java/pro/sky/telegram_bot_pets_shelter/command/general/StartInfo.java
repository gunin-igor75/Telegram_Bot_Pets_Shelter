package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

@Component
@Slf4j
public class StartInfo implements Command {
    private final MessageUtils messageUtils;

    public StartInfo(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        return messageUtils.generationSendMessage(update,
                "Выберите команду 'start' для связи с ботом.");
    }
}