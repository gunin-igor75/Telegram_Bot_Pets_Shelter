package pro.sky.telegram_bot_pets_shelter.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BuilderKeyboard {
    public InlineKeyboardMarkup createInlineKey(Map<String, String> mapCommand) {
        checkedEmpty(mapCommand);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
        mapCommand.forEach((key, value) -> {
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(value);
            button.setCallbackData(key);
            list.add(button);
            listButton.add(list);
        });
        markup.setKeyboard(listButton);
        return markup;
    }

    public ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRow = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("help"));
        KeyboardButton button = new KeyboardButton("contacts");
        button.setRequestContact(true);
        row.add(button);
        keyboardRow.add(row);
        markup.setKeyboard(keyboardRow);
        return markup;
    }

    private void checkedEmpty(Map<String, String> mapCommand) {
        if (mapCommand.isEmpty()) {
            log.warn("mapCommand is Empty");
            throw new IllegalArgumentException();
        }
    }
}





















