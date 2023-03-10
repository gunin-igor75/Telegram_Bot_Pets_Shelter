package pro.sky.telegram_bot_pets_shelter.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.command.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.command.cats.report.CatReport;
import pro.sky.telegram_bot_pets_shelter.command.dogs.Dogs;
import pro.sky.telegram_bot_pets_shelter.command.general.Cancel;
import pro.sky.telegram_bot_pets_shelter.command.general.HelpVolunteer;
import pro.sky.telegram_bot_pets_shelter.command.general.Start;
import pro.sky.telegram_bot_pets_shelter.command.menu.Registration;
import pro.sky.telegram_bot_pets_shelter.command.volunteer.*;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.repositories.OwnerRepository;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.enums.UserState;
import pro.sky.telegram_bot_pets_shelter.service.imp.OwnerServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.updateTextApplication;
import static pro.sky.telegram_bot_pets_shelter.Value.updateTextStart;
import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class CheckingMessageTest {
    @Mock
    private BuilderKeyboard keyboard;

    private MessageUtils messageUtils = new MessageUtils(keyboard);

    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private CheckingMessage checkingMessage;

    private Owner owner;
    private CommandStorage commandStorage;


    @Mock
    private CatService catService;

    @Mock
    private DogService dogService;

    @BeforeEach
    public void init() {
        Map<String, Command> mapCommand = Map.of(
                "start", new Start(messageUtils, ownerService),
                "cansel", new Cancel(messageUtils, ownerService),
                "cats", new Cats(keyboard,messageUtils)
        );
        checkingMessage = new CheckingMessage(new CommandStorage(mapCommand)
                , messageUtils, ownerService);
        owner = new Owner();
        owner.setUsername("Igor");
//        owner.setState(BASIC_STATE);
//        when(ownerService.findOrSaveOwner(any())).thenReturn(owner);
//        when(ownerService.findOwnerByChatId(123L)).thenReturn(owner);
    }
    private Update getUpdate(String name) {
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
    @Test
    public void startTest() {
        when(ownerService.findOrSaveOwner(any())).thenReturn(owner);
        Update update = getUpdate("/start");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("приюта!");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void canselTest() {
        Update update = getUpdate("/cansel");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Отправка");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void catsTest() {
        Update update = getUpdate("/cats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Welcome");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");

    }

//    @Test
//    public void applicationTest() {
//        SendMessage actual = checkingMessage.checkUpdate(updateTextApplication);
//        String text = actual.getText();
//        long id = Long.parseLong(actual.getChatId());
//        assertThat(id).isEqualTo(123L);
//        assertThat(text).endsWith("below:");
//        assertThat(id).isNotEqualTo(555);
//        assertThat(text).isNotEqualTo("start");
//    }
}