package pro.sky.telegram_bot_pets_shelter.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;
import pro.sky.telegram_bot_pets_shelter.component.InlineKeyboard;

@Component
public class InlineTest implements Command {
    private final MessageUtils messageUtils;

    public InlineTest(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        String[] buttons = {"inlineTest"};
        String[] callback = {"inlineTest"};
        InlineKeyboardMarkup markup = InlineKeyboard.createInlineKeyboard(
                buttons,
                callback
        );

        return messageUtils.generationSendMessage(
                update,
                markup,
                "inline"
        );
    }
}
