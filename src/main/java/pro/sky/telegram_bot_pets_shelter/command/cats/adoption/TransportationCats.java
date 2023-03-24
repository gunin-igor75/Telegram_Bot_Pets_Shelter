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
 * Данный класс формрует сообщения исходя из выбора transportation of cats
 */
@Component
public class TransportationCats implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public TransportationCats(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterCatsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "Правила перевоза животных на территории Российской Федерации требуют наличия ветеринарного паспорта и ветеринарного свидетельства форма № 1." +
                "Ветеринарный паспорт международного образца – паспорт животного с отметками обо всех необходимых прививках и информацией об обработке против экто- и эндопаразитов. Прививки должны быть сделаны не позднее 30 дней и не ранее 12 месяцев до даты путешествия." +
                "Все питомцы, поступающие в «Верные друзья», проходят необходимую вакцинацию и получают ветеринарные паспорта. Если вы выбрали в нашем приюте животное, которое было вакцинировано не менее чем 30 дней назад, ветеринарный паспорт мы выдаем вам на месте.\n" +
                "Ветеринарное свидетельство форма №1 – документ государственного образца, подтверждающий отсутствие заболеваний у питомца. Данное свидетельство требуется при транспортировке животных железнодорожным, воздушным, водным и автомобильным транспортом. " +
                "Срок действия ветеринарного свидетельства - 3 дня, включая день выдачи, до момента отправки и в течение всего пути следования. Документ заверяется подписью врача и печатью учреждения. Ветеринарное свидетельство является основным документом, " +
                "удостоверяющим личность животного на всевозможных пунктах проверки. Действительными признаются документы, выданные только государственными ветеринарными лечебницами.");
    }
}
