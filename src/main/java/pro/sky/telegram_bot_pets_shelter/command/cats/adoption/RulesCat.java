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
 * Данный класс формрует сообщения исходя из выбора rules for dating cat
 */
@Component
public class RulesCat implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public RulesCat(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"Мы только приветствуем, когда к нам приезжают в гости и помогают с уходом за животными." +
                "Приезжайте несколько раз к нам и заодно познакомитесь с местными обитателями." +
                "Понаблюдайте за ними: у кого какой характер, повадки и особенности поведения. " +
                "Задайте волонтерам следующие вопросы о возможном новом питомце:\n" +
                "узнайте возраст и особенности поведения;\n" +
                "расспросите о прошлом котика: как он попал в приют, какие у него были хозяева;\n" +
                "как он себя обычно ведет;\n" +
                "как реагирует, если оставить одного;\n" +
                "как себя ведет с другими животными, если у вас дома уже живет кто-то;\n" +
                "бывает ли на улице и любит ли сбегать туда.");
    }
}
