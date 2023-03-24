package pro.sky.telegram_bot_pets_shelter.command.dogs.shelter;

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
 * Данный класс формрует сообщения исходя из выбора shelter
 */
    @Component
    public class ShelterDogsInfo implements Command {
        private final BuilderKeyboard keyboard;
        private final MessageUtils messageUtils;

        public ShelterDogsInfo( MessageUtils messageUtils, BuilderKeyboard keyboard) {
            this.keyboard = keyboard;
            this.messageUtils = messageUtils;
        }

        @Override
        public SendMessage execute(Update update) {
            Map<String, String> mapCommand = new LinkedHashMap<>();
            mapCommand.put("addressDog", "Адрес");
            mapCommand.put("informationDog", "Информация");
            mapCommand.put("safetyDog", "Правила безопасности");
            mapCommand.put("contacts", "Оставить контакты");
            mapCommand.put("dogs", "Назад");
            InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
            String text = "Выберите интересующую Вас информацию:";
            return messageUtils.generationSendMessage(update, markup, text);
        }
}
