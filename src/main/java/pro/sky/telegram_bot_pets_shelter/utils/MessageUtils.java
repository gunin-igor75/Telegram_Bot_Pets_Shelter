package pro.sky.telegram_bot_pets_shelter.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;

import java.util.List;
import java.util.Random;


/**
 * *  Утилитный клас, который содержит методы по формированию
 * SenDMessage
 */
@Component
@Slf4j
public class MessageUtils {
    private final BuilderKeyboard keyboard;
    /**
     * Переменная CHAT_ID - chatId - хозяина бота
     */
    @Value("${telegram.bot.chat-id_1}")
    private long CHAT_ID_1;

    @Value("${telegram.bot.chat-id_2}")
    private long CHAT_ID_2;

    private Random random;

    public MessageUtils(BuilderKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * Данный мметод генерирует сообщение пользователю
     *
     * @param update - входящий параметр, который содержит всю входящую
     *               информацию о взаимодействии с ботом
     * @param text   - текст сообщения который будет отправлен пользователю
     *               взаимодействующему с ботом
     * @return - возвращает SendMessage для дальнейшей отправки пользователю
     */
    public SendMessage generationSendMessage(Update update, String text) {
        var response = new SendMessage();
        var chatId = getChatId(update);
        response.setChatId(chatId);
        response.setText(text);
        return response;
    }

    /**
     * Данный метод генерирует сообщение пользователю с отображением
     * клавитуры
     *
     * @param update -входящий параметр, который содержит всю входящую
     *               информацию о взаимодействии с ботом
     * @param markup - встроенная клавиатура
     * @param text   - текст сообщения который будет отправлен с клавиатурой
     * @return - возвращает SendMessage для дальнейшей отправки пользователю
     */

    public SendMessage generationSendMessage(Update update, InlineKeyboardMarkup markup, String text) {
        var response = new SendMessage();
        var chatId = getChatId(update);
        response.setChatId(chatId);
        response.setText(text);
        response.setReplyMarkup(markup);
        return response;
    }

    public SendMessage generationSendMessage(Update update, ReplyKeyboardMarkup markup, String text) {
        var response = new SendMessage();
        var chatId = getChatId(update);
        response.setChatId(chatId);
        response.setText(text);
        response.setReplyMarkup(markup);
        return response;
    }

    public SendMessage generationSendMessage(long chatId, String text) {
        var response = new SendMessage();
        response.setChatId(chatId);
        response.setText(text);
        return response;
    }


    public long getChatId(Update update) {
        long chatId;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getFrom().getId();
        } else if (update.getMessage().hasContact()) {
            chatId = update.getMessage().getContact().getUserId();
        } else {
            chatId = update.getMessage().getFrom().getId();
        }
        return chatId;
    }

    /**
     * Данный метод отправляет сообщение хозяину бота
     *
     * @return Возвращает сообщение хозяину чата
     */
    public SendMessage sendMessageCallOwner() {
        var response = new SendMessage();
        long chatId = getChatIdVolunteer();
        response.setChatId(chatId);
        response.setText("Хозяин помоги. Не могу решить вопрос");
        return response;
    }

    private long getChatIdVolunteer() {
        List<Long> chatIds = List.of(CHAT_ID_1, CHAT_ID_2);
        random = new Random();
        int index = random.nextInt(chatIds.size());
        return chatIds.get(index);
    }
}
