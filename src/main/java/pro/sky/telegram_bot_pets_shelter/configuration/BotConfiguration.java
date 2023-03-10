package pro.sky.telegram_bot_pets_shelter.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Класс, получающий имя и токен бота
 */
@Configuration
@Data
public class BotConfiguration {
    /**
     * имя бота полученного из application.properties
     */
    @Value("${telegram.bot.name}")
    private String name;

    /**
     * токен бота, полученный из переменной среды окружения операционной системы
     */
    @Value("${TELEGRAM_BOT_TOKEN_TEST}")
    private String token;
}
