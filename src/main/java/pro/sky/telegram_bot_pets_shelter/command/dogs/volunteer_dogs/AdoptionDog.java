package pro.sky.telegram_bot_pets_shelter.command.dogs.volunteer_dogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

@Component
@Slf4j
public class AdoptionDog implements Command {
    private final OwnerService ownerService;
    private final DogService dogService;
    private final MessageUtils messageUtils;

    public AdoptionDog(OwnerService ownerService, DogService dogService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.dogService = dogService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        long idDog = getIdDog(update);
        var chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        Dog persistentDog = dogService.findDog(idDog);
        String text;
        if (persistentDog == null) {
            log.error("persistentDog is null registration");
            throw new DogNotFoundException("persistentDog is null registration");
        }
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        if (!persistentOwner.getRegistration()) {
            text = "Вы не зарегистрированы !";
            return messageUtils.generationSendMessage(update, text);
        }

        boolean adoption = ownerService.checkNoAdoptionDog(persistentOwner);
        if (!adoption) {
            text = "У вас есть один песик на испытательном сроке";
            return messageUtils.generationSendMessage(update, text);
        }
        Dog modifiedDog = updateDog(persistentDog);
        updateOwner(persistentOwner, persistentDog);
        text = "Поздравляем. Песик " + modifiedDog.getName() + " принадлежит Вам.";
        return messageUtils.generationSendMessage(update, text);
    }

    private long getIdDog(Update update) {
        var callbackQuery = update.getCallbackQuery();
        var data = callbackQuery.getData();
        return Long.parseLong(data.split("\\s+")[0]);
    }

    private Dog updateDog(Dog dog) {
        LocalDate currentDate = LocalDate.now();
        dog.setAdopted(false);
        dog.setDateAdoption(currentDate);
        return dogService.editDog(dog);
    }

    private void updateOwner(Owner owner, Dog dog) {
        owner.setDog(dog);
        ownerService.editOwner(owner);
    }
}
