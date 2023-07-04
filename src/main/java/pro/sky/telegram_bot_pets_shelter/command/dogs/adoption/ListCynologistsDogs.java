package pro.sky.telegram_bot_pets_shelter.command.dogs.adoption;


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
 * Данный класс формрует сообщения исходя из выбора list of famous cynologists
 */
@Component
public class ListCynologistsDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public ListCynologistsDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"Список известных кинологов.\n"+
                "Ирина Соловьева,/n"+
                "Кира Петрушина,/n"+
                "Анна Трояновская,/n"+
                "Ольга Кормщикова,/n"+
                "Глеб Мойсеенко,/n"+
                "Александр Буйнов,/n"+
                "Агата Крайноваа,/n"+
                "Егор Горбунов,/n"+
                "Анастасия Батыгина,/n"+
                "Алексей Сычов.");
    }
}
