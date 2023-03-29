package pro.sky.telegram_bot_pets_shelter.command.cats.volunteer_cats;

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
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TakeCatsTest {

    @Mock
    private MessageUtils messageUtils;
    @Mock
    private CatService cattService;
    @Mock
    private BuilderKeyboard keyboard;

    @InjectMocks
    private TakeCats takeCats;

    @Test
    void executeCatsNoEmptyTest() {
        List<Cat> cats = getCats();
        when(cattService.getAllCatsFree()).thenReturn(cats);

        Update update = new Update();

        takeCats.execute(update);

        ArgumentCaptor<LinkedHashMap<String, String>>  mapArgumentCaptor =
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
        assertThat(actualText).startsWith("Выбрать кошку");
    }

    @Test
    void executeCatsIsEmpty() {
        List<Cat> cats = new ArrayList<>();
        when(cattService.getAllCatsFree()).thenReturn(cats);

        Update update = new Update();
        takeCats.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(1))
                .generationSendMessage(updateArgumentCaptor.capture(), textArgumentCaptor.capture());
        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).isEqualTo("Нет доступных кошек");
    }

    private List<Cat> getCats() {
        Cat catFirst = Cat.builder()
                .id(1L)
                .name("first")
                .build();
        Cat catSecond = Cat.builder()
                .id(2L)
                .name("second")
                .build();
        return List.of(catFirst, catSecond);
    }
}