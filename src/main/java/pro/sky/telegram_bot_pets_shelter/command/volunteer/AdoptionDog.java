package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
public class AdoptionDog implements Command {
    private final OwnerServiceImpl ownerService;
    private final DogService dogService;
    private final MessageUtils messageUtils;

    public AdoptionDog(OwnerServiceImpl ownerService, DogService dogService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.dogService = dogService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        String text;
        long id = Long.parseLong(update.getCallbackQuery().getData().split("\\s+")[0]);
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        Optional<Owner> ownerOptional = ownerService.findOwnerByChatId(chatId);
        Optional<Dog> dogOptional = dogService.findDog(id);
        if (ownerOptional.isEmpty()) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        if (dogOptional.isEmpty()) {
            text = "No such dog";
            return messageUtils.generationSendMessage(update, text);
        }
        Owner owner = ownerOptional.get();
        boolean adoption = ownerService.checkAdoptionDog(owner);
        if (!adoption) {
            text = "Do you have one dog on probation";
            return messageUtils.generationSendMessage(update, text);
        }
        Dog dog = dogOptional.get();
        dog.setAdopted(false);
        dog.setDate(LocalDate.now());
        owner.setDog(dog);
        dogService.editDog(dog);
        ownerService.editOwner(owner);
        text = "Congratulations. The dog " + dog.getName() + " belongs to you.";
        return messageUtils.generationSendMessage(update, text);
    }
}
