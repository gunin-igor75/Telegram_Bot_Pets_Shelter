package pro.sky.telegram_bot_pets_shelter.command.cats.report;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CatReport implements Command {
    private final BuilderKeyboard keyboard;
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;

    public CatReport(MessageUtils messageUtils, BuilderKeyboard keyboard,
                     OwnerService ownerService) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        long chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        if (persistentOwner == null || persistentOwner.getCat() == null) {
            return messageUtils.generationSendMessage(update,
                    "Эта опция доступна только владельцам наших питомцев");
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("infoReport", "Информация");
        mapCommand.put("sendReportCat", "Отправить отчет");
        mapCommand.put("cancel", "Закрыть");
        mapCommand.put("cats", "Назад");
        var markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup,
                "Выберите бота из списка ниже:");
    }
}
