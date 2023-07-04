package pro.sky.telegram_bot_pets_shelter.command.cats.shelter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Данный класс формрует сообщения исходя из выбора shelter
 */
@Component
public class ShelterCatsInfo implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;

    public ShelterCatsInfo(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("addressCat", "Адрес");
        mapCommand.put("informationCat", "Информация");
        mapCommand.put("safetyCat", "Правила безопасности");
        mapCommand.put("contacts", "Оставить контакты");
        mapCommand.put("cats", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Выберите интересующую Вас информацию:";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}

