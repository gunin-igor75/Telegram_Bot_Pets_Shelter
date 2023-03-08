package pro.sky.telegram_bot_pets_shelter;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Value {
    public static Update updateTextStart;
    public static Update updateTextApplication;
    public static Update updateKeyboardText;

    static {
        updateTextStart = new Update();
        Message message = new Message();
        Chat chat = new Chat(123L, "private");
        message.setChat(chat);
        message.setMessageId(492);
        message.setDate(1678033579);
        message.setText("/start");
        updateTextStart.setMessage(message);
    }
    static {
        updateKeyboardText = new Update();
        Message message = new Message();
        CallbackQuery callbackQuery = new CallbackQuery();
//        callbackQuery.s
        Chat chat = new Chat(123L, "private");
        message.setChat(chat);
        message.setMessageId(492);
        message.setDate(1678033579);
        message.setText("/start");
        updateTextStart.setMessage(message);
    }

    static {
        updateTextApplication = new Update();
        Message message = new Message();
        Chat chat = new Chat(123L, "private");
        message.setChat(chat);
        message.setMessageId(492);
        message.setDate(1678033579);
        message.setText("/application");
        updateTextApplication.setMessage(message);
    }

}
