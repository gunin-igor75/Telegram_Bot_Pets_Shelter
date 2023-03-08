package pro.sky.telegram_bot_pets_shelter.command;

import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Класс содержащий хранилище команд, поддерживаемый ботом,
 * автоматически инжектит в мапу все бины Command
 * ключ - название бина
 * значение - сам бин
 */
@Component
@Getter
public class CommandStorage {

    private final Map<String, Command> storage;

    public CommandStorage(Map<String, Command> storage) {
        this.storage = storage;
    }
}
