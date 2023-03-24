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
 * Данный класс формрует сообщения исходя из выбора keeping disabilities dogs
 */
@Component
public class KeepingDisabilitiesDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingDisabilitiesDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterDogsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "Наиболее частыми травмами молодых собак являются потери одной или нескольких конечностей. " +
                "Несмотря на отчаяние и душевную боль владельца, в таких случаях, после того, как заживет рана, животное чувствует себя полностью здоровым и может продолжать жить интересной жизнью. " +
                "Недостаток одной лапы собаки компенсируют довольно быстро, научившись ходить и даже бегать на трех ногах. " +
                "В данном случае владелец должен следить, чтобы животное получало адекватную физическую нагрузку – ведь собака не осознает, что теперь ей нужно немного поберечь себя. " +
                "Длительные прогулки, активные виды спорта и прыжки в высоту под запретом для таких питомцев.\n" +
                "Еще одной достаточно распространенной проблемой является потеря зрения или слуха животного. Чаще всего это происходит в старости, но иногда, в случае травмы или врожденных патологий, этими недугами страдают и достаточно молодые животные. " +
                "Лишившись одного из органов чувств, животное, как правило, компенсирует недостаток. Так слепые собаки привыкают ориентироваться на нюх и память, а те, кто плохо слышат – отлично различают вибрацию и внимательно реагируют на любые изменения в окружающем пространстве." +
                "Хозяева глухих собак зачастую признаются, что даже когда питомец уже полностью оглох, что было подтверждено медицинскими обследованиями, по его виду и поведению никогда нельзя было заподозрить, что он не слышит ни одного звука. Животное первое по зову бежало к миске с едой и регулярно встречало хозяина с работы. " +
                "А слепые собаки очень быстро учатся ориентироваться в незнакомом месте и даже, также по признанию хозяина, умудряются найти одну единственную дырку в заборе и убежать на поиски приключений.\n" +
                "Соблюдение несложных правил техники безопасности и необходимость гулять с такой собакой только на поводке.");
    }
}