package pro.sky.telegram_bot_pets_shelter.command.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
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
        var chatId = update.getCallbackQuery().getFrom().getId();
        var telegramUser = update.getCallbackQuery().getFrom();
        System.out.println(telegramUser);
        var persistentOwner = ownerService.findOwnerByChatId(chatId);
        String text;
        if (persistentOwner == null) {
            ownerService.findOrSaveOwner(telegramUser);
            text = "Congratulations. You have successfully registered";
        } else {
            text = "Sorry. You are already registered";
        }
        return messageUtils.generationSendMessage(update, text);
    }
}
