package pro.sky.telegram_bot_pets_shelter.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.command.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.command.cats.adoption.*;
import pro.sky.telegram_bot_pets_shelter.command.general.*;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.owner;
import static pro.sky.telegram_bot_pets_shelter.Value.ownerBS;

@ExtendWith(MockitoExtension.class)
class CheckingMessageTest {
    @Mock
    private BuilderKeyboard keyboard;
    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private CheckingMessage checkingMessage;
    @Mock
    private CatService catService;
    @Mock
    private DogService dogService;
    private MessageUtils messageUtils = new MessageUtils(keyboard);
    private CommandStorage commandStorage;

    @BeforeEach
    public void init() {
        Map<String, Command> mapCommand = new HashMap<>();
        mapCommand.put("start", new Start(messageUtils, ownerService));
        mapCommand.put("cansel", new Cancel(messageUtils, ownerService));
        mapCommand.put("cats", new Cats(keyboard, messageUtils));
        mapCommand.put("startInfo", new StartInfo(messageUtils));
        mapCommand.put("contacts", new Contacts(messageUtils, keyboard));
        mapCommand.put("helpVolunteer", new HelpVolunteer(messageUtils));
        mapCommand.put("cynologistTipsCats", new CynologistTipsCats(messageUtils, keyboard));
        mapCommand.put("documentsCat", new DocumentsCat(messageUtils, keyboard));
        mapCommand.put("keepingAdultCats", new KeepingAdultCats(messageUtils, keyboard));
        mapCommand.put("keepingDisabilitiesCats", new KeepingAdultCats(messageUtils, keyboard));
        mapCommand.put("listCynologistsCats", new ListCynologistsCats(messageUtils, keyboard));
        mapCommand.put("refusalsCats", new RefusalsCats(messageUtils, keyboard));
        mapCommand.put("rulesCat", new RulesCat(messageUtils, keyboard));
        mapCommand.put("shelterCatsAdoption", new ShelterCatsAdoption(messageUtils, keyboard));
        mapCommand.put("transportationCats", new TransportationCats(messageUtils, keyboard));

        checkingMessage = new CheckingMessage(new CommandStorage(mapCommand)
                , messageUtils, ownerService);

    }

    @Test
    public void startTest() {
        when(ownerService.findOrSaveOwner(any())).thenReturn(owner);
        Update update = getUpdateMess("/start");
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
        Update update = getUpdateMess("/cansel");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Отправка");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void startInfoTest() {
        Update update = getUpdateMess("/startInfo");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("select");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void contactsTest() {
        Update update = getUpdateCall("contacts");
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Leave");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void helperVolunteerTest() {
        Update update = getUpdateMess("Абракадабра");
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(0);
        assertThat(text).startsWith("Хозяин");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void catsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("cats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Welcome");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void cynologistTipsCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("cynologistTipsCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Tips");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void documentsCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("documentsCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("List");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void keepingAdultCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingAdultCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Recommendation");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void keepingDisabilitiesCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingDisabilitiesCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("keeping");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void listCynologistsCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("listCynologistsCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("List");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }
    @Test
    public void refusalsCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("refusalsCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("reasons");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void rulesCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("rulesCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Rules");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void shelterCatsAdoptionTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("shelterCatsAdoption");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Select");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }
    @Test
    public void transportationCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("transportationCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Recommendations");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    private Update getUpdateMess(String name) {
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

    private Update getUpdateCall(String name) {
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

    @Test
    public void test() {
        Cat cat = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();
        System.out.println(cat);
    }
}