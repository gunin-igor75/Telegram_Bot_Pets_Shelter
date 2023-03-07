package pro.sky.telegram_bot_pets_shelter.command.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
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
        long id = Long.parseLong(update.getCallbackQuery().getData().split("\\s+")[0]);
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        Optional<Owner> ownerOptional = ownerService.findOwnerByChatId(chatId);
        Optional<Cat> catOptional = catService.findCat(id);
        if (ownerOptional.isEmpty()) {
            text = "You are not registered";
            return messageUtils.generationSendMessage(update, text);
        }
        if (catOptional.isEmpty()) {
            text = "No such cat";
            return messageUtils.generationSendMessage(update, text);
        }
        Owner owner = ownerOptional.get();
        boolean adoption = ownerService.checkAdoptionCat(owner);
        if (!adoption) {
            text = "Do you have one cat on probation";
            return messageUtils.generationSendMessage(update, text);
        }
        Cat cat = catOptional.get();
        cat.setAdopted(false);
        cat.setDate(LocalDate.now());
        owner.setCat(cat);
        catService.editCat(cat);
        ownerService.editOwner(owner);
        text = "Congratulations. The cat " + cat.getName() + " belongs to you.";
        return messageUtils.generationSendMessage(update, text);
    }
}
