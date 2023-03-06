package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class VolunteerDogs implements Command {
    private final BuilderKeyboard keyboard;

    private final MessageUtils messageUtils;

    public VolunteerDogs(BuilderKeyboard keyboard, MessageUtils messageUtils) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put( "registration", "registration");
        mapCommand.put("takeDogs", "take a dogs");
        mapCommand.put("dogs", "back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Choose a bot from the list below:";
        return messageUtils.generationSendMessage(update,markup,text);
    }
}
