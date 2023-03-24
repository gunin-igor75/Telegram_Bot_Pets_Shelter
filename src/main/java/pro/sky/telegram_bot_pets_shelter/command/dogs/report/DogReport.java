package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class DogReport implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;

    public DogReport( MessageUtils messageUtils, BuilderKeyboard keyboard, OwnerService ownerService) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var chatId = messageUtils.getChatId(update);
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        if (persistentOwner == null || persistentOwner.getDog() == null) {
            return messageUtils.generationSendMessage(update,
                    "Эта опция доступна только владельцам наших питомцев");
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("infoReport", "Информация");
        mapCommand.put("sendReportDog", "Отправить отчет");
        mapCommand.put("cancel", "Закрыть");
        mapCommand.put("dogs", "Назад");
        var markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup,
                "Выберите бота из списка ниже:");
    }
}
