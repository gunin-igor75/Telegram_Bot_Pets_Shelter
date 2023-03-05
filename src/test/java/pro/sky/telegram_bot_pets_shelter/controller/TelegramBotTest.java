package pro.sky.telegram_bot_pets_shelter.controller;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.command.CommandStorage;
import pro.sky.telegram_bot_pets_shelter.command.Start;
import pro.sky.telegram_bot_pets_shelter.configuration.BotConfiguration;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class TelegramBotTest {

    @Mock
    private BotConfiguration configuration;


    private MessageUtils messageUtils;

    private CommandStorage commandStorage;


    private TelegramBot telegramBot;

    private long CHAT_ID = 1998202918;


    @BeforeEach
    public void init() {
        Map<String, Command> map = new HashMap<>();
        messageUtils = new MessageUtils();
        map.put("start", new Start(messageUtils));
        commandStorage = new CommandStorage(map);
        telegramBot = new TelegramBot(configuration, messageUtils,commandStorage);
    }

//    @Test
//    public void startTest() throws  TelegramApiException {
//        Update update = new Update();
//        Message message = new Message();
//        Chat chat = new Chat(123L, "private");
//        message.setChat(chat);
//        message.setMessageId(492);
//        message.setDate(1678033579);
//        message.setText("/start");
//        update.setMessage(message);
//        telegramBot.onUpdateReceived(update);
//        SendMessage actual = commandStorage.getStorage().get("start").execute(update);
//
//        Assertions.assertThat(Long.parseLong(actual.getChatId())).isEqualTo(123L);
//
//    }
}