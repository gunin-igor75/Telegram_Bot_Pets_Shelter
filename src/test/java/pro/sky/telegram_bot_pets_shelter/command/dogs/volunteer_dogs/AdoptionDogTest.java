package pro.sky.telegram_bot_pets_shelter.command.dogs.volunteer_dogs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Owner;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdoptionDogTest {

    @Autowired
    private OwnerService ownerService;
    @Autowired
    private DogService dogService;
    @Mock
    private MessageUtils messageUtils;

    private AdoptionDog adoptionDog;

    @BeforeEach
    void  init() {
        adoptionDog = new AdoptionDog(ownerService, dogService, messageUtils);
    }

    @Test
    void executeAdoptedTrueTest() {
        givenDog();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionDog.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Поздравляем. Песик");

        Owner owner = ownerService.findOwnerByChatId(100L);
        Dog transientDog = owner.getDog();
        Dog dog = dogService.findDog(transientDog.getId());
        assertThat(dog.getName()).isEqualTo("Bim");
        assertThat(dog.getId()).isEqualTo(1L);
        assertThat(dog.getAdopted()).isFalse();
        assertThat(dog.getDateAdoption()).isEqualTo(LocalDate.now());
    }

    @Test
    void catNotFoundTest() {
        givenDog();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("3", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        assertThatThrownBy(() -> adoptionDog.execute(update)).isInstanceOf(DogNotFoundException.class);

    }

    @Test
    void ownerNotFoundTest() {
        givenDog();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Update update = getUpdateCall("1", "Igor", 200L);

        when(messageUtils.getChatId(update)).thenReturn(200L);

        assertThatThrownBy(() -> adoptionDog.execute(update)).isInstanceOf(OwnerNotFoundException.class);

    }

    @Test
    void ownerNotRegistration() {
        givenDog();
        Owner igor = creteRegistrationOwner("Igor", 100L, false);
        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionDog.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Вы не зарегистрированы");
    }

    @Test
    void ownerAdoptionTrue() {
        givenDog();
        Owner igor = creteRegistrationOwner("Igor", 100L, true);
        Dog dog = Dog.builder()
                .name("sharick")
                .adopted(false)
                .dateAdoption(LocalDate.now())
                .build();
        Dog sharick = dogService.createDog(dog);
        igor.setDog(sharick);
        ownerService.editOwner(igor);

        Update update = getUpdateCall("1", "Igor", 100L);

        when(messageUtils.getChatId(update)).thenReturn(100L);

        adoptionDog.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("У вас есть один песик");
    }

    private void givenDog() {
        createDog("Bim");
        createDog("Bam");
    }


    private void createDog(String name) {
        Dog dog = Dog.builder()
                .name(name)
                .build();
        dogService.createDog(dog);
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