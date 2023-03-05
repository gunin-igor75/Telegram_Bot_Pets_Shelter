package pro.sky.telegram_bot_pets_shelter.command.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Pet;
import pro.sky.telegram_bot_pets_shelter.service.PetServiceImpl;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TakePet implements Command {
    private final MessageUtils messageUtils;
    private final PetServiceImpl petService;
    private final BuilderKeyboard keyboard;


    public TakePet(MessageUtils messageUtils, PetServiceImpl petService, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.petService = petService;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        String text;
        List<Pet> pets = petService.getAllPetsFree();
        if (pets.isEmpty()) {
            text = "No available pets";
            return messageUtils.generationSendMessage(update, text);
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        pets.forEach(pet -> mapCommand.put(String.valueOf(pet.getId()), pet.getName()));
        mapCommand.put("application", "Back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        text = "Choose a pet";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
