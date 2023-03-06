package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;


@Component
@Slf4j
@Transactional
public class Registration implements Command {
    private final OwnerServiceImpl ownerService;
    private final MessageUtils messageUtils;

    public Registration(OwnerServiceImpl ownerService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String userName = update.getCallbackQuery().getMessage().getChat().getUserName();
        Owner owner = Owner.builder()
                .chatId(chatId)
                .name(userName)
                .registeredAt(LocalDate.now())
                .build();
        boolean register = ownerService.createOwner(owner);
        String text;
        if (register) {
            text = "Congratulations. You have successfully registered";
        } else {
            text = "Sorry. You are already registered";
        }
        return messageUtils.generationSendMessage(update, text);
    }
}
