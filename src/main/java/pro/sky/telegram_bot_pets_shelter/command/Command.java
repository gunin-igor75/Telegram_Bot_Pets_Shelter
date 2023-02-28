package pro.sky.telegram_bot_pets_shelter.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс команд
 */
public interface Command {

    /**
     *  Данный методформирует логику ответа бота на вводимую команду
     * @param update - входящий параметр, который содержит всю входящую
     *              информацию о взаимодействии с ботом
     * @return - возвращает SendMessage для дальнейшей отправки пользователю
     */
    SendMessage execute(Update update);

}
