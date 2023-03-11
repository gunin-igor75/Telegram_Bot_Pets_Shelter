package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.REPORT_CATS_STATE;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.REPORT_DOGS_STATE;

@Component
@Slf4j
public class SendReportDog implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;

    public SendReportDog(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var id = messageUtils.getChatId(update);
        ownerService.editOwnerState(id, REPORT_DOGS_STATE);
        var text = "Отправляйте отчет согласно инструкции";
        return messageUtils.generationSendMessage(update, text);
    }
}
