package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.CatServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TakeCats implements Command {
    private final MessageUtils messageUtils;
    private final CatService cattService;
    private final BuilderKeyboard keyboard;


    public TakeCats(MessageUtils messageUtils, CatService catService, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.cattService = catService;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        String text;
        List<Cat> cats = cattService.getAllCatsFree();
        if (cats.isEmpty()) {
            text = "No available cats";
            return messageUtils.generationSendMessage(update, text);
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        cats.forEach(cat -> mapCommand.put(String.valueOf(cat.getId()), cat.getName()));
        mapCommand.put("volunteerCats", "Back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        text = "Choose a cat";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
