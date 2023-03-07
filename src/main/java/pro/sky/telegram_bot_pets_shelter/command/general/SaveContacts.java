package pro.sky.telegram_bot_pets_shelter.command.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Visitor;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.VisitorService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.Optional;

@Component
@Slf4j
public class SaveContacts implements Command {

    private final MessageUtils messageUtils;
    private final VisitorService visitorService;
    private final OwnerService ownerService;
    public SaveContacts(MessageUtils messageUtils, VisitorService visitorService, OwnerService ownerService) {
        this.messageUtils = messageUtils;
        this.visitorService = visitorService;
        this.ownerService = ownerService;
    }

    @Override
    public SendMessage execute(Update update) {
        long chatId = update.getMessage().getContact().getUserId();
        String phoneNumber = update.getMessage().getContact().getPhoneNumber();
        String name = update.getMessage().getContact().getFirstName();
        Optional<Visitor> visitorOpt = visitorService.findVisitorByChatId(chatId);
        Optional<Owner> ownerOpt = ownerService.findOwnerByChatId(chatId);
        if (ownerOpt.isPresent()) {
            Owner owner = ownerOpt.get();
            ownerService.editOwner(owner);
        } else if (visitorOpt.isPresent()) {
            Visitor visitor = visitorOpt.get();
            visitorService.editVisitor(visitor);
        } else {
            Visitor newVisitor = Visitor.builder()
                    .chatId(chatId)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .build();
            visitorService.creteVisitor(newVisitor);
        }
        return messageUtils.generationSendMessage(update,"Contacts saved");
    }
}
