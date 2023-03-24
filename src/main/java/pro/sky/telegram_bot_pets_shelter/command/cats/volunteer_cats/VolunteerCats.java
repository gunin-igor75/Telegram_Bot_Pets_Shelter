package pro.sky.telegram_bot_pets_shelter.command.cats.volunteer_cats;

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
public class VolunteerCats implements Command {

    private final BuilderKeyboard keyboard;

    private final MessageUtils messageUtils;

    public VolunteerCats(BuilderKeyboard keyboard, MessageUtils messageUtils) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("takeCats", "Выбрать кошку");
        mapCommand.put("cats", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Выберите бота из списка ниже:";
        return messageUtils.generationSendMessage(update,markup,text);
    }
}
