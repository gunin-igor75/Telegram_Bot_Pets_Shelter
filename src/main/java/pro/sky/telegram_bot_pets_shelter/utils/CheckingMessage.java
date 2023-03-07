package pro.sky.telegram_bot_pets_shelter.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.CommandStorage;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;

@Component
public class CheckingMessage {
    /**
     * Пефикс входящего сообщения
     */
    private final String PREFIX = "/";
    /**
     * бин, содержащий ассоциативный массив, где
     * ключ - название бина(класса отвечающий за действие в ответ на команду бота)
     * значение - сам бин
     */
    private final CommandStorage commandStorage;

    private SendMessage message;
    public CheckingMessage(CommandStorage commandStorage) {
        this.commandStorage = commandStorage;
    }

    public SendMessage checkUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText() &&
                update.getMessage().getText().startsWith(PREFIX)) {
            String key = update.getMessage().getText().split("\\s+")[0].substring(1);
            System.out.println(update.getMessage().getContact());
            message = commandStorage.getStorage().get(key).execute(update);
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
            String key = update.getCallbackQuery().getData();
            if (commandStorage.getStorage().containsKey(key)) {
                message = commandStorage.getStorage().get(key).execute(update);
                System.out.println(update);
                System.out.println(update.getCallbackQuery().getChatInstance());
                System.out.println(update.getCallbackQuery().getMessage().getChatId());
                System.out.println(update.getCallbackQuery().getMessage().getText());
            } else if (Character.isDigit(key.charAt(0))){
                message = commandStorage.getStorage().get(key.split("\\s+")[1]).execute(update);
            }
        } else if (update.hasMessage() && update.getMessage().hasContact()) {
            message = commandStorage.getStorage().get("saveContacts").execute(update);
        } else {
            message = commandStorage.getStorage().get("helpVolunteer").execute(update);
        }
        return message;
    }


}
