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

    public ShelterDogsAdoption(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("rulesDog", "Правила знакомства с собаками");
        mapCommand.put("documentsDog", "Список необходимых документов для взятия собаки из приюта");
        mapCommand.put("transportationDogs", "Рекомендации по транспортировке собаки");
        mapCommand.put("keepingDogs", "Рекомендации по содержанию собак");
        mapCommand.put("keepingAdultDogs", "Рекомендации по содержанию взрослых собак");
        mapCommand.put("keepingDisabilitiesDogs", "Рекомендации по содержанию собак с ограниченными возможностями");
        mapCommand.put("cynologistTipsDogs", "Советы опытного кинолога");
        mapCommand.put("listCynologistsDogs", "Список известных кинологов");
        mapCommand.put("refusalsDogs", "Список причин отказа");
        mapCommand.put("contacts", "Контакты для связи");
        mapCommand.put("dogs", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Выберите интересующую Вас информацию:";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
