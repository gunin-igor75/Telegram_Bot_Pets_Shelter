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

    public ShelterCatsAdoption( MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("rulesCat", "Правила знакомства с кошками");
        mapCommand.put("documentsCat", "Список необходимых документов для взятия кота из приюта");
        mapCommand.put("transportationCats", "Рекомендации по транспортировке кота");
        mapCommand.put("keepingCat", "Рекомендации по содержанию кошек");
        mapCommand.put("keepingAdultCats", "Рекомендации по содержанию взрослых кошек");
        mapCommand.put("keepingDisabilitiesCats", "Рекомендации по содержанию кошек с ограниченными возможностями");
        mapCommand.put("refusalsCats", "Список причин отказа");
        mapCommand.put("contacts", "Контакты для связи");
        mapCommand.put("cats", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Выберите интересующую Вас информацию:";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}

