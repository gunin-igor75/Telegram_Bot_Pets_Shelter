package pro.sky.telegram_bot_pets_shelter;

import org.telegram.telegrambots.meta.api.objects.*;

import pro.sky.telegram_bot_pets_shelter.entity.*;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.*;

public class Value {
    public static Owner ownerNull;
    public static Owner ownerId;
    public static Owner ownerEdit;
    public static Owner owner;
    public static Owner ownerBS;
    public static Owner ownerBSStart;
    public static Owner ownerBSRegistrationTrue;
    public static Owner ownerRCS;
    public static Owner ownerRDS;
    public static Owner ownerCats;
    public static Owner ownerDogs;
    public static Cat cat;

    public static Cat newCat;

    public static Cat catFirst;

    public static Cat catSecond;

    public static Dog dog;
    public static Dog newDog;
    public static Dog dogFirst;
    public static Dog dogSecond;

    public static BlackList blackListFirst;
    public static BlackList blackListSecond;

    public static Task taskFirst;
    public static Task taskSecond;

    public static Volunteer volunteerFirst;
    public static Volunteer volunteerSecond;

    public static Report reportFirst;
    public static Report reportSecond;

    static {
        ownerNull = Owner.builder()
                .username("Oleg")
                .build();
        ownerId = Owner.builder()
                .id(11L)
                .chatId(123)
                .username("Oleg")
                .build();
        ownerEdit = Owner.builder()
                .id(11L)
                .chatId(123)
                .username("Oleg")
                .firstname("Oleg")
                .lastname("Petrov")
                .registration(true)
                .lastAction("/start")
                .state(REPORT_CATS_STATE)
                .build();

        owner = Owner.builder()
                .chatId(123)
                .registration(false)
                .username("Oleg")
                .build();

        ownerBS = Owner.builder()
                .chatId(123)
                .username("Oleg")
                .registration(false)
                .state(BASIC_STATE)
                .build();

        ownerBSStart =Owner.builder()
                .chatId(123)
                .firstname("Oleg")
                .lastname("Petrov")
                .username("Oleg")
                .registration(false)
                .lastAction("start")
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
                .chatId(123L)
                .username("Oleg")
                .dog(new Dog())
                .state(BASIC_STATE)
                .build();

        cat = Cat.builder()
                .id(10L)
                .adopted(true)
                .name("kisa")
                .dateAdoption(LocalDate.now())
                .build();

        newCat = Cat.builder()
                .id(10L)
                .adopted(false)
                .name("kisa")
                .dateAdoption(LocalDate.now())
                .build();

        catFirst = Cat.builder()
                .name("kisa")
                .build();

        catSecond = Cat.builder()
                .id(1L)
                .name("murzik")
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



        blackListFirst = BlackList.builder()
                .username("Oleg")
                .chatId(100L)
                .build();

        blackListSecond = BlackList.builder()
                .id(200L)
                .chatId(100L)
                .username("Oleg")
                .build();

        taskFirst = Task.builder()
                .done(false)
                .build();

        taskSecond = Task.builder()
                .id(200L)
                .done(false)
                .build();

        volunteerFirst = Volunteer.builder()
                .chatId(111L)
                .username("Igor")
                .build();

        volunteerSecond = Volunteer.builder()
                .id(200L)
                .chatId(111L)
                .username("Igor")
                .build();

        reportFirst = Report.builder()
                .fileId("1212131313")
                .chatId(123L)
                .healthStatus("diet")
                .build();

        reportSecond = Report.builder()
                .id(200L)
                .fileId("1212131313")
                .chatId(123L)
                .healthStatus("diet")
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
