package pro.sky.telegram_bot_pets_shelter.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@Slf4j
public class BuilderInlineKeyboard {
    public InlineKeyboardMarkup createInlineKey(LinkedHashMap<String, String> mapCommand) {
        if (mapCommand.isEmpty()) {
            log.warn("mapCommand is Empty");
            throw new IllegalArgumentException();
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
        mapCommand.forEach((key, value) -> {
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton elemKeyboard = new InlineKeyboardButton();
            elemKeyboard.setText(value);
            elemKeyboard.setCallbackData(key);
            list.add(elemKeyboard);
            listButton.add(list);
        });
        markup.setKeyboard(listButton);
        return markup;
    }
}





















