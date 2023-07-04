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
 * Данный класс формрует сообщения исходя из выбора keeping cats
 */
@Component
public class KeepingDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterDogsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "Основа этого процесса — кормление, выгул , ветеринарный контроль и гигиена, а также дрессировка.\n" +
                "Кормление-Главная ошибка, которую совершает большинство владельцев собак, — кормление домашней едой. Еще хуже, когда питомца угощают лакомствами, которые человек считает вкусными: сахар, шоколад, колбаса. Лучшее питание для животного — специализированный корм и лакомства. " +
                "И к ним собаку стоит приучать с детства.\n" +
                "Выгул-Выгуливать собаку нужно дважды в день, как минимум — утром и вечером, особенно если речь идет о крупных породах. В связи с развитой мускулатурой им требуется большая физическая нагрузка, а значит, и прогулки должны быть дольше. " +
                "Маленькие собачки, наоборот, не требуют многочасовых выгулов. Обычно после 5–10 минут они сами просятся домой.\n" +
                "Дрессировка-Каждая собака должна знать команды «фу» и «ко мне», которые часто используются в повседневной жизни. Дрессировать собаку можно как самостоятельно, так и прибегнув к помощи специалиста.\n" +
                "Ветеринарный контроль и гигиена-Помимо сбалансированного питания и физической активности, здоровье собаки зависит и от своевременного посещения ветеринарного врача. Животному необходим ветеринарный паспорт, в котором должны быть отмечены все прививки. " +
                "Кроме того, рекомендуется один раз в полгода посещать клинику для планового осмотра.\n" +
                "В уходе за собакой важны гигиенические процедуры. Это, в первую очередь, купание, расчесывание и уход за когтями и зубами питомца.");
    }
}
