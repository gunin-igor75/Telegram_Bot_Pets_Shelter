package pro.sky.telegram_bot_pets_shelter.command.cats.volunteer_cats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.imp.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AdoptionCat implements Command {
    private final OwnerService ownerService;
    private final CatService catService;
    private final MessageUtils messageUtils;

    public AdoptionCat(OwnerServiceImpl ownerService, CatService catService, MessageUtils messageUtils) {
        this.ownerService = ownerService;
        this.catService = catService;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage execute(Update update) {
        var idCat = Long.parseLong(update.getCallbackQuery().getData().split("\\s+")[0]);
        var chatId = messageUtils.getChatId(update);
        Owner persistentOwner = ownerService.findOwnerByChatId(chatId);
        Cat persistentCat = catService.findCat(idCat);
        if (persistentOwner == null) {
            log.error("persistentOwner is null registration");
            throw new OwnerNotFoundException();
        }
        if (!persistentOwner.getRegistration()) {
            return messageUtils.generationSendMessage(update,"Вы не зарегистрированы !");
        }
        if (persistentCat == null) {
            return messageUtils.generationSendMessage(update, "Нет таких кошек");
        }
        boolean adoption = ownerService.checkAdoptionCat(persistentOwner);
        if (!adoption) {
            return messageUtils.generationSendMessage(update, "У вас есть один кот на испытательном сроке");
        }
        persistentCat.setAdopted(false);
        persistentCat.setDateAdoption(LocalDate.now());
        persistentOwner.setCat(persistentCat);
        catService.editCat(persistentCat);
        ownerService.editOwner(persistentOwner);
        var text = "Поздравляем. Кот " + persistentCat.getName() + " принадлежит Вам.";
        return messageUtils.generationSendMessage(update, text);
    }
}
