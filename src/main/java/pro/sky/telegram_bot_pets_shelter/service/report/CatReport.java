package pro.sky.telegram_bot_pets_shelter.service.report;

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

    public CatReport(BuilderKeyboard keyboard, MessageUtils messageUtils, OwnerService ownerService) {
        this.keyboard = keyboard;
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var persistentOwner = ownerService.findOwnerByChatId(
                update.getCallbackQuery().getFrom().getId());
        if (persistentOwner == null) {
            return messageUtils.generationSendMessage(update,
                    "This option is only available to owners of our pets");
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        mapCommand.put("infoReport", "Information");
        mapCommand.put("sendReport", "Send report");
        mapCommand.put("cancel", "Cancel");
        mapCommand.put("cats", "back");
        var markup = keyboard.createInlineKey(mapCommand);
        return messageUtils.generationSendMessage(update, markup,
                "Choose a bot from the list below:");
    }
}
