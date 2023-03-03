package pro.sky.telegram_bot_pets_shelter.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BuilderKeyboard {
    public InlineKeyboardMarkup createInlineKey(Map<String, String> mapCommand) {
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

    public InlineKeyboardMarkup createInlineKeyApp() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("registration");
        button.setCallbackData("registration");
        List<InlineKeyboardButton> list = new ArrayList<>();
        list.add(button);
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("adoption");
        button1.setCallbackData("adoption");
        list.add(button1);
        List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
        listButton.add(list);
        markup.setKeyboard(listButton);
        return markup;
    }
}





















