package pro.sky.telegram_bot_pets_shelter.command.dogs.volunteer_dogs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TakeDogsTest {

    @Mock
    private MessageUtils messageUtils;
    @Mock
    private DogService dogService;
    @Mock
    private BuilderKeyboard keyboard;

    @InjectMocks
    private TakeDogs takeDogs;

    @Test
    void executeCatsNoEmptyTest() {
        List<Dog> dogs = getDogs();
        when(dogService.getAllDogsFree()).thenReturn(dogs);

        Update update = new Update();

        takeDogs.execute(update);

        ArgumentCaptor<LinkedHashMap<String, String>> mapArgumentCaptor =
                ArgumentCaptor.forClass(LinkedHashMap.class);
        verify(keyboard, times(1)).createInlineKey(mapArgumentCaptor.capture());
        LinkedHashMap<String, String> actualMap = mapArgumentCaptor.getValue();
        assertThat(actualMap.size()).isEqualTo(3);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<InlineKeyboardMarkup> inlineKeyboardMarkupArgumentCaptor =
                ArgumentCaptor.forClass(InlineKeyboardMarkup.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                inlineKeyboardMarkupArgumentCaptor.capture(), textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Выбрать собаку");
    }

    @Test
    void executeCatsIsEmpty() {
        List<Dog> dogs = new ArrayList<>();
        when(dogService.getAllDogsFree()).thenReturn(dogs);

        Update update = new Update();
        takeDogs.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1))
                .generationSendMessage(updateArgumentCaptor.capture(), textArgumentCaptor.capture());
        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).isEqualTo("Нет доступных собак");
    }

    private List<Dog> getDogs() {
        Dog dogFirst = Dog.builder()
                .id(1L)
                .name("first")
                .build();
        Dog dogSecond = Dog.builder()
                .id(2L)
                .name("second")
                .build();
        return List.of(dogFirst, dogSecond);
    }
}