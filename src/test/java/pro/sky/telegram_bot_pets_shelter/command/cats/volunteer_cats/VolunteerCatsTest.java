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
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerCatsTest {

    @Mock
    private BuilderKeyboard keyboard;

    @Mock
    private  MessageUtils messageUtils;

    @InjectMocks
    private VolunteerCats volunteerCats;

    @Test
    void execute() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        when(keyboard.createInlineKey(any())).thenReturn(markup);

        Update update = new Update();
        volunteerCats.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);
        ArgumentCaptor<InlineKeyboardMarkup> inlineKeyboardMarkupArgumentCaptor =
                ArgumentCaptor.forClass(InlineKeyboardMarkup.class);
        ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(messageUtils, times(1)).generationSendMessage(updateArgumentCaptor.capture(),
                inlineKeyboardMarkupArgumentCaptor.capture(), textArgumentCaptor.capture());

        String actualText = textArgumentCaptor.getValue();
        assertThat(actualText).startsWith("Выберите кошку");
    }
}