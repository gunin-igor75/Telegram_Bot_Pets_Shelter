package pro.sky.telegram_bot_pets_shelter.command.cats.volunteer_cats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdoptionCatTest {
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private CatService catService;
    @Mock
    private MessageUtils messageUtils;

    private AdoptionCat adoptionCat;

    @BeforeEach
    void  init() {
        adoptionCat = new AdoptionCat(ownerService, catService, messageUtils);
    }

    @Test
    void executeAdoptedTrueTest() {
        givenCat();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionCat.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Поздравляем. Кот");

        Owner owner = ownerService.findOwnerByChatId(100L);
        Cat transientCat = owner.getCat();
        Cat cat = catService.findCat(transientCat.getId());
        assertThat(cat.getName()).isEqualTo("mur");
        assertThat(cat.getId()).isEqualTo(1L);
        assertThat(cat.getAdopted()).isFalse();
        assertThat(cat.getDateAdoption()).isEqualTo(LocalDate.now());
    }

    @Test
    void catNotFoundTest() {
        givenCat();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("3", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        assertThatThrownBy(() -> adoptionCat.execute(update)).isInstanceOf(CatNotFoundException.class);

    }

    @Test
    void ownerNotFoundTest() {
        givenCat();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("1", "Igor", 200L);

        when(messageUtils.getChatId(update)).thenReturn(200L);

        assertThatThrownBy(() -> adoptionCat.execute(update)).isInstanceOf(OwnerNotFoundException.class);

    }

    @Test
    void ownerNotRegistration() {
        givenCat();
        Owner igor = creteRegistrationOwner("Igor", 100L, false);
        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionCat.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Вы не зарегистрированы");
    }

    @Test
    void ownerAdoptionTrue() {
        givenCat();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Cat cat = Cat.builder()
                .name("kisa")
                .adopted(false)
                .dateAdoption(LocalDate.now())
                .build();
        Cat kisa = catService.createCat(cat);
        igor.setCat(kisa);
        ownerService.editOwner(igor);

        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionCat.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("У вас есть один кот");
    }

    private void givenCat() {
        createCat("mur");
        createCat("myu");
    }


    private void createCat(String name) {
        Cat cat = Cat.builder()
                .name(name)
                .build();
        catService.createCat(cat);
    }

    private Owner creteRegistrationOwner(String username, long chatId,
                                         boolean registration) {
        LocalDate dateRegistration = LocalDate.now();
        Owner owner = Owner.builder()
                .username(username)
                .chatId(chatId)
                .dateRegistration(dateRegistration)
                .registration(registration)
                .build();
       return ownerService.createOwner(owner);
    }

    private Update getUpdateCall(String idPet, String username, long chatId) {
        Update update = new Update();
        CallbackQuery call = new CallbackQuery();
        User user = new User();
        user.setUserName(username);
        user.setId(chatId);
        call.setFrom(user);
        call.setData(idPet);
        update.setCallbackQuery(call);
        return update;
    }
}