package pro.sky.telegram_bot_pets_shelter.command.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegram_bot_pets_shelter.exception_handling.OwnerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.getUpdateCall;

@ExtendWith(MockitoExtension.class)
class RegistrationProcessTest {
    @Mock
    private MessageUtils messageUtils;
    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private RegistrationProcess registrationProcess;

    @Test
    void executeTest() {
        when(ownerService.findOwnerByChatId(123L)).thenReturn(null);
        Update update = getUpdateCall("registrationProcess");
        assertThatThrownBy(() -> {
            registrationProcess.execute(update);
        }).isInstanceOf(OwnerNotFoundException.class);
    }
}