package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

/**
 * Данный класс формрует сообщения исходя из выбора contacts
 *
 */
@Component
@Slf4j
public class Contacts implements Command {
    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public Contacts(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        ReplyKeyboardMarkup markup = keyboard.createKeyboard();
        return messageUtils.generationSendMessage(update, markup, "Оставить контакты");
    }
}