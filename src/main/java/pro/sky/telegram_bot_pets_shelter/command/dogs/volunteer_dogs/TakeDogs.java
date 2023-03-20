package pro.sky.telegram_bot_pets_shelter.command.dogs.volunteer_dogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TakeDogs implements Command {
    private final MessageUtils messageUtils;
    private final DogService dogService;
    private final BuilderKeyboard keyboard;


    public TakeDogs(MessageUtils messageUtils, DogService dogService, BuilderKeyboard keyboard) {
        this.messageUtils = messageUtils;
        this.dogService = dogService;
        this.keyboard = keyboard;
    }

    @Override
    public SendMessage execute(Update update) {
        String text;
        List<Dog> dogs = dogService.getAllDogsFree();
        if (dogs.isEmpty()) {
            text = "No available dogs";
            return messageUtils.generationSendMessage(update, text);
        }
        Map<String, String> mapCommand = new LinkedHashMap<>();
        dogs.forEach(dog -> mapCommand.put(dog.getId() + " adoptionDog", dog.getName()));
        mapCommand.put("volunteerDogs", "Back");
        InlineKeyboardMarkup markup = keyboard.createInlineKey(mapCommand);
        text = "Choose a dog";
        return messageUtils.generationSendMessage(update, markup, text);
    }
}
