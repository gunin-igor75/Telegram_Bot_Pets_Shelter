package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.Command;

@Component
@Slf4j
public class DogSaveReport implements Command {
    @Override
    public SendMessage execute(Update update) {
        return null;
    }
}
