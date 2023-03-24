package pro.sky.telegram_bot_pets_shelter.service.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;

import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.schedule.extension.NotReportsOneDayServiceCatsExt;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotReportsOneDayServiceTest {
    @Mock
    private MessageUtils messageUtils;
    @Mock
    private TelegramBot telegramBot;

    @Mock
    private CatService catService;

    @InjectMocks
    private NotReportsOneDayServiceCatsExt notReportsOneDayService;
    @Test
    void sendMessageEveryDayNoReports() {
        List<Report> reportsMaxDate = givenReportsMaxDate();
        Mockito.when(notReportsOneDayService.getReportMaxDate()).thenReturn(reportsMaxDate);
        SendMessage message = new SendMessage();
        when(messageUtils.generationSendMessage(any(Long.class), any(String.class))).thenReturn(
                message
        );
        notReportsOneDayService.sendMessageEveryDayNoReports();
        ArgumentCaptor<Long> argLong = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);
        verify(messageUtils, times(2))
                .generationSendMessage(argLong.capture(), argString.capture());
        List<Long> actualChatId = argLong.getAllValues();
        List<String> actualTexts = argString.getAllValues();
        String actualText = actualTexts.get(0);
        List<Long> expectedChatIds = givenExpectedChatId();
        assertThat(actualChatId).containsExactlyInAnyOrderElementsOf(expectedChatIds);
        assertThat(actualTexts.size()).isEqualTo(2);
        assertThat(actualText).startsWith("Прошу отправить");
        verify(telegramBot, times(2)).sendAnswerMessage(
                any(SendMessage.class)
        );
    }

    private List<Report> givenReportsMaxDate() {
        LocalDate currentDate = LocalDate.now();
        Report reportFirst = getReport(currentDate.minusDays(3), 1L, 100L);
        Report reportSecond = getReport(currentDate.minusDays(2), 3L, 200L);
        Report reportThird = getReport(currentDate, 1L, 300L);
        return List.of(reportFirst,reportSecond,reportThird);
    }

    private List<Long> givenExpectedChatId() {
        return List.of(100L, 200L);
    }

    private Report getReport(LocalDate dateReport, long id, long chatId) {
        return Report.builder()
                .id(id)
                .dateReport(dateReport)
                .chatId(chatId)
                .build();
    }
}












