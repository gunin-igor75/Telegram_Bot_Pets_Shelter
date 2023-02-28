package pro.sky.telegram_bot_pets_shelter.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pro.sky.telegram_bot_pets_shelter.configuration.BotConfiguration;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfiguration botConfiguration;


    public TelegramBot(BotConfiguration botConfiguration) {
        super(botConfiguration.getToken());
        this.botConfiguration = botConfiguration;
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getName();
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (TelegramApiException e) {
            log.error("Error register bot", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
