package pro.sky.telegram_bot_pets_shelter.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.telegram_bot_pets_shelter.command.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.command.dogs.Dogs;
import pro.sky.telegram_bot_pets_shelter.command.dogs.ShelterDogsAdoption;
import pro.sky.telegram_bot_pets_shelter.command.dogs.ShelterDogsInfo;
import pro.sky.telegram_bot_pets_shelter.command.volunteer.*;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerServiceImpl;
import pro.sky.telegram_bot_pets_shelter.service.CatServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.sky.telegram_bot_pets_shelter.Value.updateTextApplication;
import static pro.sky.telegram_bot_pets_shelter.Value.updateTextStart;


class CheckingMessageTest {
    private MessageUtils messageUtils;
    private CheckingMessage checkingMessage;
    private CommandStorage commandStorage;
    @Mock
    private OwnerServiceImpl ownerService;

    @Mock
    private CatService catService;

    @Mock
    private DogService dogService;

    private BuilderKeyboard keyboard;

    {
        messageUtils = new MessageUtils();
        Map<String, Command> map = new HashMap<>();
        map.put("start", new Start(messageUtils));
        keyboard = new BuilderKeyboard();
        map.put("dogs", new Dogs(keyboard, messageUtils));
        map.put("shelterDogsAdoption", new ShelterDogsAdoption());
        map.put("shelterDogsInfo", new ShelterDogsInfo());
        map.put("cats", new Cats(keyboard, messageUtils));
        map.put("shelterCatsAdoption", new ShelterDogsAdoption());
        map.put("shelterCatsInfo", new ShelterDogsInfo());
        map.put("report", new Report());
        map.put("helpVolunteer ", new HelpVolunteer(messageUtils));
        map.put("volunteerCats", new VolunteerCats(keyboard,messageUtils));
        map.put("volunteerDogs", new VolunteerDogs(keyboard,messageUtils));
//        map.put("takeDogs", new TakeDogs())

        map.put("registration", new Registration(ownerService,messageUtils));
        map.put("takeDogs", new TakeDogs(messageUtils, dogService,keyboard));
        map.put("takeCats", new TakeCats(messageUtils, catService,keyboard));
        commandStorage = new CommandStorage(map);
        checkingMessage = new CheckingMessage(commandStorage);
    }

    @Test
    public void startTest() {
        SendMessage actual = checkingMessage.checkUpdate(updateTextStart);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("приюта!");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void applicationTest() {
        SendMessage actual = checkingMessage.checkUpdate(updateTextApplication);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("below:");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }
}