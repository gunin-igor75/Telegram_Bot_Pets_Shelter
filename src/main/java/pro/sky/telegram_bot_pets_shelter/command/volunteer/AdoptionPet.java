package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.service.CatServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
public class AdoptionPet implements Command {
    private final OwnerServiceImpl ownerService;
    private final CatServiceImpl petService;
    private final MessageUtils messageUtils;

    public AdoptionPet(OwnerServiceImpl ownerService, CatServiceImpl petService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.messageUtils = messageUtils;
    }

    @Override
    @Transactional
    public SendMessage execute(Update update) {
        String text;
        long id = Long.parseLong(update.getCallbackQuery().getData());
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        Optional<Owner> ownerOptional = ownerService.findOwnerByChatId(chatId);
        Optional<Cat> petOptional = petService.findCat(id);
        if (ownerOptional.isEmpty()) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        if (petOptional.isEmpty()) {
            text = "No such cat";
            return messageUtils.generationSendMessage(update, text);
        }
        Owner owner = ownerOptional.get();
        Cat cat = petOptional.get();
        cat.setAdopted(true);
        cat.setDate(LocalDate.now());
        owner.setCat(cat);
        text = "Congratulations. The cat " + cat.getName() + " belongs to you.";
        return messageUtils.generationSendMessage(update, text);
    }
}
