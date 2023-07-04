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
 * Данный класс формрует сообщения исходя из выбора information
 */
@Component
public class InformationDog implements Command {
    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public InformationDog(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterDogsInfo", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "«Верные друзья»  — современный приют для кошек и собак, созданная при поддержке фонда «Вольное Дело». Приют «Верные друзья» находятся в Алма Ате. На сегодняшний день более 1450 кошек и 1330 собак из приюта нашли новых хозяев.\n" +
                "Миссия приюта «Верные друзья» – формирование культуры гуманного и ответственного отношения к животным.");
    }
}
