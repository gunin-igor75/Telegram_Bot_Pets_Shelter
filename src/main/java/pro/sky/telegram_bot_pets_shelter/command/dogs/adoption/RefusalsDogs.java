package pro.sky.telegram_bot_pets_shelter.command.dogs.adoption;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс формрует сообщения исходя из выбора reasons for refusal
 */
@Component
public class RefusalsDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public RefusalsDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterDogsAdoption", "Back");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"List of reasons for refusal.");
    }
}
