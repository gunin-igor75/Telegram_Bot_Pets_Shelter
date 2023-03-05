package pro.sky.telegram_bot_pets_shelter.command.app;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

@Component
public class Application implements Command {

    private final BuilderKeyboard keyboard;

    private final MessageUtils messageUtils;

    public Application(BuilderKeyboard keyboard, MessageUtils messageUtils) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        InlineKeyboardMarkup markup = keyboard.createInlineKeyApp();
        String text = "Choose a bot from the list below:";
        return messageUtils.generationSendMessage(update,markup,text);
    }
}
