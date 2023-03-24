package pro.sky.telegram_bot_pets_shelter.command.cats.shelter;

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
 * Данный класс формрует сообщения исходя из выбора address
 */
@Component
public class AddressCat implements Command {

        private final MessageUtils messageUtils;
        private final BuilderKeyboard keyboard;

        public AddressCat(MessageUtils messageUtils, BuilderKeyboard keyboard) {
            this.messageUtils = messageUtils;
            this.keyboard = keyboard;
        }

        @Override
        public SendMessage execute (Update update){
            Map<String, String> mapCommand=new HashMap<>();
            mapCommand.put("shelterCatsInfo", "Назад");
            InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
            return messageUtils.generationSendMessage(update,markup,"Адрес приюта «Верные друзья»: Жетысу-2, дом 58А, Алматы, Казахстан");
        }
    }

