package pro.sky.telegram_bot_pets_shelter.command.cats.volunteer_cats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

@Component
@Slf4j
public class AdoptionCat implements Command {
    private final OwnerService ownerService;
    private final CatService catService;
    private final MessageUtils messageUtils;

    public AdoptionCat(OwnerService ownerService, CatService catService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.catService = catService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        long idCat = getIdCat(update);
        var chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        Cat persistentCat = catService.findCat(idCat);
        String text;
        if (persistentCat == null) {
            log.error("persistentCat is null registration");
            throw new CatNotFoundException("persistentCat is null registration");
        }
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        if (!persistentOwner.getRegistration()) {
            text = "Вы не зарегистрированы !";
            return messageUtils.generationSendMessage(update, text);
        }

        boolean adoption = ownerService.checkNoAdoptionCat(persistentOwner);
        if (!adoption) {
            text = "У вас есть один кот на испытательном сроке";
            return messageUtils.generationSendMessage(update, text);
        }
        Cat modifiedCat = updateCat(persistentCat);
        updateOwner(persistentOwner, persistentCat);
        text = "Поздравляем. Кот " + modifiedCat.getName() + " принадлежит Вам.";
        return messageUtils.generationSendMessage(update, text);
    }

    private long getIdCat(Update update) {
        var callbackQuery = update.getCallbackQuery();
        var data = callbackQuery.getData();
        return Long.parseLong(data.split("\\s+")[0]);
    }

    private Cat updateCat(Cat cat) {
        LocalDate currentDate = LocalDate.now();
        cat.setAdopted(false);
        cat.setDateAdoption(currentDate);
        return catService.editCat(cat);
    }

    private void updateOwner(Owner owner, Cat cat) {
        owner.setCat(cat);
        ownerService.editOwner(owner);
    }
}
