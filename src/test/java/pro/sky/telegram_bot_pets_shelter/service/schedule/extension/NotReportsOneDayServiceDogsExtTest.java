package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotReportsOneDayServiceDogsExtTest {

    @Mock
    private MessageUtils messageUtils;
    @Mock
    private TelegramBot telegramBot;

    @Mock
    private DogService dogService;

    @InjectMocks
    private NotReportsOneDayServiceDogsExt notReportsOneDayServiceDogsExt;

    @Test
    void getReportMaxDate() {
        List<Report> reports = givenReportsMaxDate();
        when(dogService.getReportMaxDate()).thenReturn(reports);
        notReportsOneDayServiceDogsExt.getReportMaxDate();
        verify(dogService, times(1)).getReportMaxDate();
    }

    private List<Report> givenReportsMaxDate() {
        LocalDate currentDate = LocalDate.now();
        Report reportFirst = getReport(currentDate.minusDays(3), 1L, 100L);
        Report reportSecond = getReport(currentDate.minusDays(2), 3L, 200L);
        Report reportThird = getReport(currentDate, 1L, 300L);
        return List.of(reportFirst,reportSecond,reportThird);
    }

    private Report getReport(LocalDate dateReport, long id, long chatId) {
        return Report.builder()
                .id(id)
                .dateReport(dateReport)
                .chatId(chatId)
                .build();
    }
}



















