package pro.sky.telegram_bot_pets_shelter.command.cats.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.REPORT_CATS_STATE;

@Component
@Slf4j
public class SendReportCat implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;

    public SendReportCat(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        setStateOwner(update);
        var text = "Отправляйте отчет согласно инструкции";
        return messageUtils.generationSendMessage(update, text);
    }

    private void setStateOwner(Update update) {
        var chatId = messageUtils.getChatId(update);
        var owner = ownerService.findOwnerByChatId(chatId);
        owner.setState(REPORT_CATS_STATE);
        ownerService.editOwner(owner);
    }
}
