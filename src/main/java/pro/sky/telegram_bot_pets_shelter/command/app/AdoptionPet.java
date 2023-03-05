package pro.sky.telegram_bot_pets_shelter.command.app;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.service.PetServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.Optional;

@Component
@Slf4j
public class AdoptionPet implements Command {
    private final OwnerServiceImpl ownerService;
    private final PetServiceImpl petService;
    private final MessageUtils messageUtils;

    public AdoptionPet(OwnerServiceImpl ownerService, PetServiceImpl petService, MessageUtils messageUtils) {
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
        Optional<Pet> petOptional = petService.findPet(id);
        if (ownerOptional.isEmpty()) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        if (petOptional.isEmpty()) {
            text = "No such pet";
            return messageUtils.generationSendMessage(update, text);
        }
        Owner owner = ownerOptional.get();
        Pet pet = petOptional.get();
        pet.setAdopted(true);
        pet.setDate(LocalDate.now());
        owner.setPet(pet);
        text = "Congratulations. The pet " + pet.getName() + " belongs to you.";
        return messageUtils.generationSendMessage(update, text);
    }
}
