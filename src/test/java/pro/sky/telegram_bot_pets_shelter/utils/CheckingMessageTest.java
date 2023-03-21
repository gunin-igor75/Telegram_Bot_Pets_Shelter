package pro.sky.telegram_bot_pets_shelter.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.command.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.Cats;
import pro.sky.telegram_bot_pets_shelter.command.cats.adoption.*;
import pro.sky.telegram_bot_pets_shelter.command.cats.report.CatReport;
import pro.sky.telegram_bot_pets_shelter.command.cats.report.CatSaveReport;
import pro.sky.telegram_bot_pets_shelter.command.cats.report.SendReportCat;
import pro.sky.telegram_bot_pets_shelter.command.cats.shelter.AddressCat;
import pro.sky.telegram_bot_pets_shelter.command.cats.shelter.InformationCat;
import pro.sky.telegram_bot_pets_shelter.command.cats.shelter.SafetyCat;
import pro.sky.telegram_bot_pets_shelter.command.cats.shelter.ShelterCatsInfo;
import pro.sky.telegram_bot_pets_shelter.command.dogs.adoption.*;
import pro.sky.telegram_bot_pets_shelter.command.dogs.report.DogReport;
import pro.sky.telegram_bot_pets_shelter.command.dogs.report.DogSaveReport;
import pro.sky.telegram_bot_pets_shelter.command.dogs.report.SendReportDog;
import pro.sky.telegram_bot_pets_shelter.command.dogs.shelter.AddressDog;
import pro.sky.telegram_bot_pets_shelter.command.dogs.shelter.InformationDog;
import pro.sky.telegram_bot_pets_shelter.command.dogs.shelter.SafetyDog;
import pro.sky.telegram_bot_pets_shelter.command.dogs.shelter.ShelterDogsInfo;
import pro.sky.telegram_bot_pets_shelter.command.general.*;
import pro.sky.telegram_bot_pets_shelter.command.menu.Registration;
import pro.sky.telegram_bot_pets_shelter.command.menu.RegistrationProcess;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

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
    private ReportService reportService;
    @Mock
    private DogService dogService;
    private MessageUtils messageUtils = new MessageUtils(keyboard);
    private CommandStorage commandStorage;
    @Mock
    private BotServiceImp botServiceImp;

    @BeforeEach
    public void init() {
        Map<String, Command> mapCommand = new HashMap<>();

        mapCommand.put("start", new Start(messageUtils, ownerService));
        mapCommand.put("cancel", new Cancel(messageUtils, ownerService));
        mapCommand.put("cats", new Cats(keyboard, messageUtils, ownerService));
        mapCommand.put("startInfo", new StartInfo(messageUtils));
        mapCommand.put("contacts", new Contacts(messageUtils, keyboard));
        mapCommand.put("helpVolunteer", new HelpVolunteer(messageUtils));
        mapCommand.put("documentsCat", new DocumentsCat(messageUtils, keyboard));
        mapCommand.put("keepingAdultCats", new KeepingAdultCats(messageUtils, keyboard));
        mapCommand.put("keepingDisabilitiesCats", new KeepingAdultCats(messageUtils, keyboard));
        mapCommand.put("keepingCats", new KeepingCats(messageUtils, keyboard));
        mapCommand.put("refusalsCats", new RefusalsCats(messageUtils, keyboard));
        mapCommand.put("rulesCat", new RulesCat(messageUtils, keyboard));
        mapCommand.put("shelterCatsAdoption", new ShelterCatsAdoption(messageUtils, keyboard));
        mapCommand.put("transportationCats", new TransportationCats(messageUtils, keyboard));
        mapCommand.put("catReport", new CatReport(messageUtils, keyboard, ownerService));
        mapCommand.put("catSaveReport", new CatSaveReport(messageUtils, reportService, ownerService, catService));
        mapCommand.put("sendReportCat", new SendReportCat(messageUtils, ownerService));
        mapCommand.put("addressCat", new AddressCat(messageUtils, keyboard));
        mapCommand.put("informationCat", new InformationCat(messageUtils, keyboard));
        mapCommand.put("safetyCat", new SafetyCat(messageUtils, keyboard));
        mapCommand.put("shelterCatsInfo", new ShelterCatsInfo(messageUtils, keyboard));
        mapCommand.put("cynologistTipsDogs", new CynologistTipsDogs(messageUtils, keyboard));
        mapCommand.put("documentsDog", new DocumentsDog(messageUtils, keyboard));
        mapCommand.put("keepingAdultDogs", new KeepingAdultDogs(messageUtils, keyboard));
        mapCommand.put("keepingDisabilitiesDogs", new KeepingAdultDogs(messageUtils, keyboard));
        mapCommand.put("keepingDogs", new KeepingDogs(messageUtils, keyboard));
        mapCommand.put("listCynologistsDogs", new ListCynologistsDogs(messageUtils, keyboard));
        mapCommand.put("refusalsDogs", new RefusalsDogs(messageUtils, keyboard));
        mapCommand.put("rulesDog", new RulesDog(messageUtils, keyboard));
        mapCommand.put("shelterDogsAdoption", new ShelterDogsAdoption(messageUtils, keyboard));
        mapCommand.put("transportationDogs", new TransportationDogs(messageUtils, keyboard));
        mapCommand.put("addressDog", new AddressDog(messageUtils, keyboard));
        mapCommand.put("informationDog", new InformationDog(messageUtils, keyboard));
        mapCommand.put("safetyDog", new SafetyDog(messageUtils, keyboard));
        mapCommand.put("shelterDogsInfo", new ShelterDogsInfo(messageUtils, keyboard));
        mapCommand.put("dogReport", new DogReport(messageUtils, keyboard, ownerService));
        mapCommand.put("dogSaveReport", new DogSaveReport(messageUtils, reportService, ownerService, dogService));
        mapCommand.put("sendReportDog", new SendReportDog(messageUtils, ownerService));
        mapCommand.put("registration", new Registration(messageUtils, keyboard));
        mapCommand.put("registrationProcess", new RegistrationProcess(messageUtils,ownerService));

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
        Update update = getUpdateMess("/cancel");
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
    public void keepingCatsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingCats");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("cats.");
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

    @Test
    public void catReportOwnerCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerCats);
        Update update = getUpdateCall("catReport");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Choose");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void catReportNoOwnerCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("catReport");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("This");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void CatSaveReportTest() {
        //TODO сделать в конце тестов
    }

    @Test
    public void SendReportCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerCats);
        Update update = getUpdateCall("sendReportCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Отправляйте");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void addressCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("addressCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Cats");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void informationCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("informationCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("information.");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void safetyCatTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("safetyCat");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Safety");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void shelterCatsInfoTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("shelterCatsInfo");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Select");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void cynologistTipsDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("cynologistTipsDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Tips");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void documentsDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("documentsDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("List");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void keepingAdultDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingAdultDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Recommendation");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void keepingDisabilitiesDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingDisabilitiesDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("keeping");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void keepingDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("keepingDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Recommendation");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void listCynologistsDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("listCynologistsDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("List");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void refusalsDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("refusalsDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("reasons");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void rulesDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("rulesDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Rules");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void shelterDogsAdoptionTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("shelterDogsAdoption");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Select");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void transportationDogsTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("transportationDogs");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).contains("transporting");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void addressDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("addressDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Dogs");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void informationDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("informationDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).endsWith("information.");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void safetyDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("safetyDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Safety");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void shelterDogsInfoTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("shelterDogsInfo");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Select");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }



    @Test
    public void dogReportOwnerDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerDogs);
        Update update = getUpdateCall("dogReport");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Choose");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void dogReportNoOwnerDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("dogReport");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("This");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void DogSaveReportTest() {
        //TODO сделать в конце тестов
    }

    @Test
    public void SendReportDogTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerDogs);
        Update update = getUpdateCall("sendReportDog");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Отправляйте");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void registrationTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateMess("/registration");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Registration");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void registrationProcessFalseTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBS);
        Update update = getUpdateCall("registrationProcess");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Congratulations.");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void registrationProcessTrueTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(ownerBSRegistrationTrue);
        Update update = getUpdateCall("registrationProcess");
        SendMessage actual = checkingMessage.checkUpdate(update);
        String text = actual.getText();
        long id = Long.parseLong(actual.getChatId());
        assertThat(id).isEqualTo(123L);
        assertThat(text).startsWith("Sorry.");
        assertThat(id).isNotEqualTo(555);
        assertThat(text).isNotEqualTo("start");
    }

    @Test
    public void registrationProcessNegativeTest() {

    }
    @Test
    public void test() {
        Cat cat = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();
        System.out.println(cat);
    }
}