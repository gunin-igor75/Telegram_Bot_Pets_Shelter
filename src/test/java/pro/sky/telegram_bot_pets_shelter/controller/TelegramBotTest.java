package pro.sky.telegram_bot_pets_shelter.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static pro.sky.telegram_bot_pets_shelter.Value.updateTextStart;


@ExtendWith(MockitoExtension.class)
class TelegramBotTest {

    @Mock
    private TelegramBot telegramBot;


    @Test
    public void sendTest() {
        telegramBot.onUpdateReceived(updateTextStart);
        Mockito.verify(telegramBot).onUpdateReceived(Mockito.any());
        telegramBot.sendAnswerMessage(Mockito.any());
        Mockito.verify(telegramBot).sendAnswerMessage(Mockito.any());
    }
}