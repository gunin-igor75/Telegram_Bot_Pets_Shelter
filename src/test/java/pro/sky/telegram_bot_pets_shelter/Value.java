package pro.sky.telegram_bot_pets_shelter;

import org.telegram.telegrambots.meta.api.objects.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.*;

public class Value {
    public static Owner owner;
    public static Owner ownerBS;
    public static Owner ownerBSRegistrationTrue;
    public static Owner ownerRCS;
    public static Owner ownerRDS;
    public static Owner ownerCats;
    public static Owner ownerDogs;

    public static Dog dog;
    public static Dog newDog;
    public static Dog dogFirst;
    public static Dog dogSecond;

    static {
        owner = Owner.builder()
                .chatId(123)
                .username("Igor")
                .build();

        ownerBS = Owner.builder()
                .chatId(123)
                .username("Oleg")
                .registration(false)
                .state(BASIC_STATE)
                .build();

        ownerBSRegistrationTrue = Owner.builder()
                .chatId(123)
                .username("Zaika")
                .registration(true)
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

        ownerCats = Owner.builder()
                .chatId(123)
                .username("Oleg")
                .cat(new Cat())
                .state(BASIC_STATE)
                .build();

        ownerDogs = Owner.builder()
                .chatId(123)
                .username("Oleg")
                .dog(new Dog())
                .state(BASIC_STATE)
                .build();

        dog = Dog.builder()
                .id(10L)
                .adopted(true)
                .name("pupsick")
                .dateAdoption(LocalDate.now())
                .build();

        newDog = Dog.builder()
                .id(10L)
                .adopted(false)
                .name("pupsick")
                .dateAdoption(LocalDate.now())
                .build();


        dogFirst = Dog.builder()
                .name("pupsick")
                .build();

        dogSecond = Dog.builder()
                .id(1L)
                .name("pupsick")
                .build();


    }

    public static Update getUpdateCall(String name) {
        Update update = new Update();
        CallbackQuery call = new CallbackQuery();
        User user = new User();
        user.setUserName("Igor");
        user.setId(123L);
        call.setFrom(user);
        call.setData(name);
        update.setCallbackQuery(call);
        return update;
    }

    public static Update getUpdateMess(String name) {
        Update update = new Update();
        Message message = new Message();
        User user = new User();
        user.setUserName("Igor");
        user.setId(123L);
        message.setFrom(user);
        message.setText(name);
        update.setMessage(message);
        return update;
    }
}
