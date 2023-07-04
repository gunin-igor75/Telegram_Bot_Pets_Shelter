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
 * Данный класс формрует сообщения исходя из выбора keeping adult cats
 */
@Component
public class KeepingAdultCats implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingAdultCats(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "После того, как ваш малыш переступил возрастную отметку в 12 месяцев, кошка считается уже взрослой. " +
                "Если кошка не страдает лишним весом и ведет достаточно активный образ жизни, вы можете смело обеспечить свободный доступ к еде, кошка не будет есть больше чем требуется в любом случае. " +
                "Если кошка после стерилизации или из-за какой-либо другой причины страдает ожирением, то лучше контролировать кормление питомца ограничивая это порционным кормлением два раза в день." +
                "Как правило, взрослой кошке вполне хватает 150 - 200 грамм потребления корма в сутки при массе тела не более 4 - 5 килограмм. Следите за тем, чтобы у кошки была в миске чистая вода и меняйте ее раз в день. " +
                "Помните, употребление кипяченой воды может ухудшить качество зубов у вашего питомца. Если у питомца длинная шерсть, то придется вычесывать ее каждый день специальной щеткой. При короткой шерсти кошку протирают мокрой губкой." +
                "Мыть животное нужно 2-3 раза в год или в случае необходимости.");
    }
}