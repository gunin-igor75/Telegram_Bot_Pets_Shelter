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
public class SaveContacts implements Command {
    private final MessageUtils messageUtils;
    private final OwnerService ownerService;
    public SaveContacts(MessageUtils messageUtils, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        var chatId = messageUtils.getChatId(update);
        var phoneNumber = update.getMessage().getContact().getPhoneNumber();
        var owner = ownerService.findOwnerByChatId(chatId);
        if (owner == null) {
            var userTelegram = update.getMessage().getFrom();
            owner = ownerService.findOrSaveOwner(userTelegram);
        }
        owner.setPhoneNumber(phoneNumber);
        ownerService.editOwner(owner);
        return messageUtils.generationSendMessage(update, "Контакты успешно сохранены");
    }
}
