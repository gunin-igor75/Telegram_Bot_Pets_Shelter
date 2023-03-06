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

    public InlineKeyboardMarkup createInlineKeyApp() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button1.setText("registration");
        button1.setCallbackData("registration");
        List<InlineKeyboardButton> list = new ArrayList<>();
        list.add(button1);
        button2.setText("take a pet");
        button2.setCallbackData("takePet");
        list.add(button2);
        List<List<InlineKeyboardButton>> listButton = new ArrayList<>();
        listButton.add(list);
        markup.setKeyboard(listButton);
        return markup;
    }

    private void checkedEmpty(Map<String, String> mapCommand) {
        if (mapCommand.isEmpty()) {
            log.warn("mapCommand is Empty");
            throw new IllegalArgumentException();
        }
    }

}





















