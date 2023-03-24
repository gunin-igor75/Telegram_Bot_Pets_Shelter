package pro.sky.telegram_bot_pets_shelter.command.dogs.shelter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс формрует сообщения исходя из выбора afety
 */
@Component
public class SafetyDog implements Command {
    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public SafetyDog(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterDogsInfo", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "Правила поведения в приюте «Верные друзья»:\n" +
                "Если вы планируете погулять с собаками приюта, необходимо иметь при себе бахилы и желательно сменную одежду,\n" +
                "Проходя мимо карантинного корпуса, не гладьте собак через сетку, если после этого планируете общаться с животными из стационара." +
                "После посещения карантина Вы должны будете поменять сменную одежду и тщательно вымыть и вытереть руки,\n" +
                "Собак можно кормить только едой из приюта,\n" +
                "В приюте запрещено курить и находиться в состоянии алкогольного наркотического опьянения");
    }
}