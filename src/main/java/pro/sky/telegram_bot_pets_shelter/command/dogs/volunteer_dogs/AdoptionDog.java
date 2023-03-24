package pro.sky.telegram_bot_pets_shelter.command.dogs.volunteer_dogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.imp.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        var idDog = Long.parseLong(update.getCallbackQuery().getData().split("\\s+")[0]);
        var chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        Dog persistentDog = dogService.findDog(idDog);
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        if (!persistentOwner.getRegistration()) {
            return messageUtils.generationSendMessage(update,"Вы не зарегистрированы !");
        }
        if (persistentDog == null) {
            return messageUtils.generationSendMessage(update, "Нет таких собак");
        }
        boolean adoption = ownerService.checkAdoptionDog(persistentOwner);
        if (!adoption) {
            return messageUtils.generationSendMessage(update, "У вас есть одна собака на испытательном сроке");
        }
        persistentDog.setAdopted(false);
        persistentDog.setDateAdoption(LocalDate.now());
        persistentOwner.setDog(persistentDog);
        dogService.editDog(persistentDog);
        ownerService.editOwner(persistentOwner);
        var text = "Поздравляем. Собака " + persistentDog.getName() + " принадлежит Вам.";
        return messageUtils.generationSendMessage(update, text);
    }
}
