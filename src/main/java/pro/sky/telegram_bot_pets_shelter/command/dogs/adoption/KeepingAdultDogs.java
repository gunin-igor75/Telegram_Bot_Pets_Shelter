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
 * Данный класс формрует сообщения исходя из выбора keeping adult dogs
 */
@Component
public class KeepingAdultDogs implements Command {

    private final MessageUtils messageUtils;
    private final BuilderKeyboard keyboard;

    public KeepingAdultDogs(MessageUtils messageUtils, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new HashMap<>();
        mapCommand.put("shelterDogsAdoption", "Назад");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup, "С возрастом условия содержания собаки должны меняться. Сложность в том, что у каждой породы своя возрастная линейка. " +
                "Взросление собаки разворачивается по типичному для всех живых существ сценарию. После периода детства наступает половое созревание, за ним – взросление, и, наконец, старение. " +
                "Границы этих периодов у каждого домашнего питомца индивидуальны, поскольку зависят и от породы, и от условий содержания." +
                "Собака среднего возраста или старше – идеальный вариант для тех, кто не привык к высоким нагрузкам. Хотя она с удовольствием будет с вами подолгу гулять, и даже бегать за мячом или палкой, " +
                "и этого для вас обоих будет вполне достаточно. Если вы приютите у себя взрослую собаку, вам не нужно будет уделять много времени на ее обучение.");
    }
}
