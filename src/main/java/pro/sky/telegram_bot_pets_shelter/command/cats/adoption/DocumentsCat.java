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
 * Данный класс формрует сообщения исходя из выбора list of documents to take a cat
 */
@Component
public class DocumentsCat implements Command {
    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public DocumentsCat(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"В организации «Верные друзья» при передаче животного оформляется договор с будущим хозяином животного и для этого нужно иметь с собой паспорт. " +
                "В нем фиксируются данные обеих сторон, оговариваются пункты ответственного содержания животного. " +
                "Мы также всегда ненавязчиво отслеживаем судьбу наших бывших подопечных.");
    }
}
