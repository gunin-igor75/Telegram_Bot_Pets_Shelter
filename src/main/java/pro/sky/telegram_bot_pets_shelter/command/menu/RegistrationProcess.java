package pro.sky.telegram_bot_pets_shelter.command.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.imp.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


@Component
@Slf4j
public class RegistrationProcess implements Command {
    private final OwnerServiceImpl ownerService;
    private final MessageUtils messageUtils;

    public RegistrationProcess(OwnerServiceImpl ownerService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        long chatId = update.getCallbackQuery().getFrom().getId();
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        String text = "Congratulations. You have successfully registered";
        if (persistentOwner.getRegistration()) {
            text = "Sorry. You are already registered";
        } else {
            persistentOwner.setRegistration(true);
            ownerService.editOwner(persistentOwner);
        }
        return messageUtils.generationSendMessage(update, text);
    }
}
