package pro.sky.telegram_bot_pets_shelter;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;

import java.util.HashMap;
import java.util.Map;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.*;

public class Value {
    public static Owner owner;
    public static Owner ownerBS;
    public static Owner ownerRCS;
    public static Owner ownerRDS;
    public static Owner ownerNull;


    static {
        owner = Owner.builder()
                .chatId(123)
                .username("Igor")
                .build();

        ownerBS = Owner.builder()
                .chatId(123)
                .username("Oleg")
                .state(BASIC_STATE)
                .build();

        ownerRCS = Owner.builder()
                .chatId(123)
                .username("Ivan")
                .state(REPORT_CATS_STATE)
                .build();

        ownerRDS = Owner.builder()
                .chatId(123)
                .username("Gaga")
                .state(REPORT_DOGS_STATE)
                .build();
    }
}
