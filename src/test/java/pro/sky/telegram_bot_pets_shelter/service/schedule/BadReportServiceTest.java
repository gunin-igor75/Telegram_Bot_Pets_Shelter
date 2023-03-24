package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BadReportServiceTest {
    @Mock
    private MessageUtils messageUtils;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ReportService reportService;

    @InjectMocks
    private BadReportService badReportService;

    @Test
    void SendMessageBadReport() {
        List<Long> chatIdBadReports = List.of(100L, 200L, 300L);
        LocalDate currentDate = LocalDate.now();
        when(reportService.getChatIdBadReport(currentDate)).thenReturn(chatIdBadReports);
        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(
                message
        );
        badReportService.SendMessageBadReport();
        ArgumentCaptor<Long> argLong = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(3))
                .generationSendMessage(argLong.capture(), argString.capture());
        List<Long> actualChatId = argLong.getAllValues();
        List<String> actualTexts = argString.getAllValues();
        String actualText = actualTexts.get(0);
        assertThat(actualChatId).containsExactlyInAnyOrderElementsOf(chatIdBadReports);
        assertThat(actualTexts.size()).isEqualTo(3);
        assertThat(actualText).startsWith("Дорогой усыновитель");
        verify(telegramBot, times(3)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }
}





















