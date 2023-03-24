package pro.sky.telegram_bot_pets_shelter.command.cats.adoption;

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
 * Данный класс формрует сообщения исходя из выбора reasons for refusal
 */
@Component
public class RefusalsCats implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public RefusalsCats(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"Взять питомца из приюта «Верные друзья» не так уж легко. " +
                "Наши работники и волонтеры стараются сделать все, чтобы кошки и собаки не оказались на улице повторно, поэтому отдают животных только в надежные руки. " +
                "Существует пять причин, по которым чаще всего отказывают желающим «усыновить» домашнего любимца. " +
                "1 Большое количество животных дома " +
                "2 Нестабильные отношения в семье " +
                "3 Наличие маленьких детей " +
                "4 Съемное жилье " +
                "5 Животное в подарок или для работы.");
    }
}
