package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

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
        setStateOwner(update);
        var text = "Отправка отчета отменена";
        return messageUtils.generationSendMessage(update, text);
    }

    private void setStateOwner(Update update) {
        var chatId = messageUtils.getChatId(update);
        var owner = ownerService.findOwnerByChatId(chatId);
        owner.setState(BASIC_STATE);
        ownerService.editOwner(owner);
    }
}














