package pro.sky.telegram_bot_pets_shelter.command.cats.adoption;

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
 * Данный класс формрует сообщения исходя из выбора adoption
 *
 */
@Component
public class ShelterCatsAdoption implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;

    public ShelterCatsAdoption(BuilderKeyboard keyboard, MessageUtils messageUtils) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("rulesCat", "rules for dating cat");
        mapCommand.put("documentsCat", "list of documents to take a cat");
        mapCommand.put("transportationCats", "transportation of cats");
        mapCommand.put("keepingCat", "keeping cats");
        mapCommand.put("keepingAdultCats", "keeping adult cats");
        mapCommand.put("keepingDisabilitiesCats", "keeping disabilities cats");
        mapCommand.put("cynologistTipsCats", "experienced cynologist tips");
        mapCommand.put("listCynologistsCats", "list of famous cynologists");
        mapCommand.put("refusalsCats", "reasons for refusal");
        mapCommand.put("contacts", "contacts for communication");
        mapCommand.put("cats", "Back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Select the information you are interested in:";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}

