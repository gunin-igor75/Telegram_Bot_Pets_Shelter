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
 * Данный класс формрует сообщения исходя из выбора keeping disabilities cats
 */
@Component
public class KeepingDisabilitiesCats implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingDisabilitiesCats(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "Чаще всего слепота у кошки наступает в старости. Обычно рекомендуется оставлять все на своих местах, не меняя для нее условий проживания.\n"+
                "Еда, вода и лоток должны стоять в привычном месте. \n" +
                "Порядок в квартире или доме поможет ей беспрепятственно ходить и не натыкаться на вещи. \n" +
                "По возможности уберите все острые и опасные для животного предметы. \n" +
                "Не издавайте громких или резких звуков, оградите вашу любимицу от излишнего шума. \n" +
                "Если кошка привыкла гулять на улице, соорудите для нее специальный вольер. Для ослепшей кошки можно поставить столбики для лазанья или вертикальный игровой комплекс.\n" +
                "Не держите окна и двери открытыми, если на них нет защитной сетки.  \n" +
                "Не подходите к незрячей кошке сзади. \n" +
                "Уделяйте ей больше внимания: разговаривайте, гладьте, играйте с ней в том же объеме, как и до слепоты. Присутствие хозяина и его ласковый голос успокаивают животное. \n" +
                "Нелишним будет купить ошейник и написать на нем, что ваша кошка слепая. Не забудьте указать номер телефона для связи с вами в случае ее пропажи. \n" +
                "Кормите кошку сбалансированным кормом, расчесывайте и купайте ее.\n" +
                "Для животного можно подобрать специальные игрушки, издающие хруст, шорохи, писк и шелест. Обязательно нужны подвижные игры, чтобы у кошки не развилось ожирение. Запомните, что теперь ориентиром для слепой любимицы служит ваш голос. Поэтому поощряйте ее лакомством, когда она реагирует на ваш зов.\n" +
                "В любом случае, если вы заметили первые признаки снижения зрения у кошки, обратитесь к ветеринарному специалисту.");
    }
}
