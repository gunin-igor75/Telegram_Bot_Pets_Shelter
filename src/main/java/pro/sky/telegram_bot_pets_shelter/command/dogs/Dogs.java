package pro.sky.telegram_bot_pets_shelter.command.dogs;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Dogs implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;

    private final OwnerService ownerService;

    public Dogs(BuilderKeyboard keyboard, MessageUtils messageUtils, OwnerService ownerService) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put( "shelterDogsInfo", "Приют");
        mapCommand.put("shelterDogsAdoption", "Усыновление собак");
        mapCommand.put("dogReport", "Отчеты");
        mapCommand.put("volunteerDogs", "Волонтеры");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        String text = "Добро пожаловать в приют собак";
        saveLastAction(update);
        return messageUtils.generationSendMessage(update,markup,text);
    }

    private void saveLastAction(Update update) {
        long chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        persistentOwner.setLastAction("dogs");
        ownerService.editOwner(persistentOwner);
    }
}
