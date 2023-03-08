package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.imp.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class AdoptionCat implements Command {
    private final OwnerServiceImpl ownerService;
    private final CatService catService;
    private final MessageUtils messageUtils;

    public AdoptionCat(OwnerServiceImpl ownerService, CatService catService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.catService = catService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        String text;
        var idCat = Long.parseLong(update.getCallbackQuery().getData().split("\\s+")[0]);
        var chatIdOwner = update.getCallbackQuery().getMessage().getFrom().getId();
        Owner persistentOwner = ownerService.findOwnerByChatId(chatIdOwner);
        Cat persistentCat = catService.findCat(idCat);
        if (persistentOwner == null) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        if (persistentCat == null) {
            text = "No such cat";
            return messageUtils.generationSendMessage(update, text);
        }
        boolean adoption = ownerService.checkAdoptionCat(persistentOwner);
        if (!adoption) {
            text = "Do you have one cat on probation";
            return messageUtils.generationSendMessage(update, text);
        }
        persistentCat.setAdopted(false);
        persistentCat.setDateAdoption(LocalDateTime.now());
        persistentOwner.setCat(persistentCat);
        catService.editCat(persistentCat);
        ownerService.editOwner(persistentOwner);
        text = "Congratulations. The cat " + persistentCat.getName() + " belongs to you.";
        return messageUtils.generationSendMessage(update, text);
    }
}
