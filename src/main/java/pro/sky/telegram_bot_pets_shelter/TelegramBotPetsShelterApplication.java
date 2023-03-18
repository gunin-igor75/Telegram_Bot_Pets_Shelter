package pro.sky.telegram_bot_pets_shelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotPetsShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotPetsShelterApplication.class, args);
    }

}
