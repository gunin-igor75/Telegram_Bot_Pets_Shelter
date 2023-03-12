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
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.List;

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
        var cats = cattService.getAllCatsFree();
        if (cats.isEmpty()) {
            text = "No available cats";
            return messageUtils.generationSendMessage(update, text);
        }
        var mapCommand = new LinkedHashMap<String, String>();
        cats.forEach(cat -> mapCommand.put(cat.getId() + " adoptionCat", cat.getName()));
        mapCommand.put("volunteerCats", "Back");
        var markup = keyboard.createInlineKey(mapCommand);
        text = "Choose a cat";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
