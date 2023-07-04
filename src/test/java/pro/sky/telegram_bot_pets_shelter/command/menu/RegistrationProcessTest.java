package pro.sky.telegram_bot_pets_shelter.command.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.component.BuilderKeyboard;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;


import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.getUpdateCall;


@ExtendWith(MockitoExtension.class)
class RegistrationProcessTest {

    @Mock
    private BuilderKeyboard keyboard;
    private MessageUtils messageUtils;
    @Spy
    private OwnerService ownerService;

    private RegistrationProcess registrationProcess;

    @BeforeEach
    public void init() {
        messageUtils = new MessageUtils(new BuilderKeyboard());
        registrationProcess = new RegistrationProcess(messageUtils, ownerService);
    }

    @Test
    void executeTest() {
        Update update = getUpdateCall("registrationProcess");
        String text = "Congratulations";
        when(ownerService.registration(123L)).thenReturn(text);
        registrationProcess.execute(update);
        SendMessage message = messageUtils.generationSendMessage(update, text);
        Assertions.assertThat(message.getText()).isEqualTo(text);
    }
}