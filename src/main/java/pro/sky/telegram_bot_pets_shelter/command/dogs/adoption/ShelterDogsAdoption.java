package pro.sky.telegram_bot_pets_shelter.command.dogs.adoption;

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
public class ShelterDogsAdoption implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;

    public ShelterDogsAdoption(BuilderKeyboard keyboard, MessageUtils messageUtils) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("rulesDog", "rules for dating dog");
        mapCommand.put("documentsDog", "list of documents to take a dog");
        mapCommand.put("transportationDogs", "transportation of dogs");
        mapCommand.put("keepingDogs", "keeping dogs");
        mapCommand.put("keepingAdultDogs", "keeping adult dogs");
        mapCommand.put("keepingDisabilitiesDogs", "keeping disabilities dogs");
        mapCommand.put("cynologistTipsDogs", "experienced cynologist tips");
        mapCommand.put("listCynologistsDogs", "list of famous cynologists");
        mapCommand.put("refusalsDogs", "reasons for refusal");
        mapCommand.put("contacts", "contacts for communication");
        mapCommand.put("dogs", "Back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Select the information you are interested in:";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
