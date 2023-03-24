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
 * Данный класс формрует сообщения исходя из выбора experienced cynologist tips
 */
@Component
public class CynologistTipsDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public CynologistTipsDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute (Update update){
        Map<String, String> mapCommand=new HashMap<>();
        mapCommand.put("shelterDogsAdoption", "Назад");
        InlineKeyboardMarkup markup=keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update,markup,"Советы опытного кинолога\n"+
                "Если вы завели себе собаку и хотите, чтобы ваша совместная жизнь была комфортной, радостной и безопасной, важно уделить внимание дрессировке и воспитанию. " +
                "Становление полноценной собаки включает в себя целый комплекс мероприятий: кормление и уход, физическое развитие, воспитание, обучение и тренировка собаки, ветеринарное обслуживание. " +
                "Хорошо иметь воспитанную собаку. Она везде желанный гость, потому что хорошо ведет себя как в обществе людей, так и других собак. Она умеет оставаться на своем месте и подходить, когда позовут. " +
                "С ней приятно выйти на прогулку, потому как она хорошо ходит рядом, не тянет поводок, умеет носить намордник и не бегает за кошками и другими животными. Она не страдает фобиями (не боится резких звуков, " +
                "общественного транспорта, посещения ветбольницы, других собак), спокойно остается дома одна, не портит вещи. Не требует, чтобы ее кормили первой, не попрошайничает, не ворует еду со стола. Не занимает без разрешения место хозяина. " +
                "Справляет нужду только в специально отведенных для этого местах." +
                "Следует учитывать, что воспитательная дрессировка щенка и дрессировка взрослой собаки сильно отличаются. Воспитанием щенков следует заниматься вскоре после рождения, наиболее подходящий возраст – 3,5 — 4 месяца.");
    }
}
