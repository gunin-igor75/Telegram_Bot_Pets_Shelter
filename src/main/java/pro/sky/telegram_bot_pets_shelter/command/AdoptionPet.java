package pro.sky.telegram_bot_pets_shelter.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.service.PetServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.Optional;

@Component
@Slf4j
public class AdoptionPet implements Command{
    private final OwnerServiceImpl ownerService;
    private final PetServiceImpl petService;
    private final MessageUtils messageUtils;


    public AdoptionPet(OwnerServiceImpl ownerService, PetServiceImpl petService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.messageUtils = messageUtils;
    }


    @Override
    public SendMessage execute(Update update) {
        String text;
        long id = Long.parseLong(update.getCallbackQuery().getData());
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        Optional<Owner> ownerOptional = ownerService.findOwnerByChatId(chatId);
        if (ownerOptional.isEmpty()) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        Owner owner = ownerOptional.get();

        return null;
    }
}
