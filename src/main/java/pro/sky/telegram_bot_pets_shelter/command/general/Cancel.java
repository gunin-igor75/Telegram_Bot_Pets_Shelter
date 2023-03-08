package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

@Component
@Slf4j
public class Cancel implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;

    public Cancel(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var persistentOwner = ownerService.findOwnerByChatId(
                update.getCallbackQuery().getFrom().getId());
        if (persistentOwner == null) {
            log.error("Owner is null");
        }
        return null;
    }
}
