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
 * Данный класс формрует сообщения исходя из выбора keeping cats
 */
@Component
public class KeepingCats implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingCats(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "К появлению кошки в доме требуется подготовить все самое необходимое. Предлагаем ознакомиться со списком основных предметов, необходимых кошке, и требований к условиям ее содержания.\n" +
                "Лежанка. Это удобное место для сна, где котенок или кошка будут чувствовать себя комфортно и безопасно. Рекомендуется расположить лежанку повыше, желательно в тихом, безопасном для котенка месте, вдали от сквозняков.\n" +
                "Лоток. Он должен быть достаточно глубоким, но не слишком, так как высокие стенки могут отпугнуть котенка. Вам также понадобится совок для удаления экскрементов.\n" +
                "Миски для воды. У кошки всегда должен быть доступ к свежей воде. Можно использовать фонтанчики как альтернативу мискам, поскольку кошкам, как известно, нравится смотреть, как льется вода. Это привлекает их внимание и побуждает пить воду.\n" +
                "Миски для корма. Вам потребуется небольшая миска для корма. Ее необходимо установить на достаточном расстоянии от миски для воды во избежание загрязнения воды. Кроме того, можно воспользоваться «пищевыми головоломками». Они особенно хорошо подойдут для кошки, постоянно содержащейся в помещении." +
                "Такая кормушка станет отличным стимулом для развития умственных способностей, источником физических нагрузок и средством для раскрытия природного охотничьего инстинкта животного. Необходимо дать кошке время привыкнуть к такой кормушке, поэтому не следует сразу же полностью заменять «головоломкой» миску для корма.\n" +
                "Игровой комплекс для кошек. Это дополнительное приобретение — не просто развлечение. Оно имеет и ряд полезных функций, так как позволяет кошке точить когти, лазать и прятаться в укрытии, а также способствует хорошему самочувствию животного и реализации его природных инстинктов.\n" +
                "Рекомендуется приобрести несколько игровых комплексов для кошек и установить их в разных местах дома, чтобы кошка могла найти тихий уголок вдали от гостей и громкого шума, где она при необходимости может скрыться от посторонних глаз. ");
    }
}