package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotReportsTwoDayServiceDogsExtTest {

    @Mock
    private OwnerService ownerService;
    @Mock
    private BlackListService blackListService;
    @Mock
    private DogService dogService;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private TaskService taskService;

    @InjectMocks
    private NotReportsTwoDayServiceDogsExt notReportsTwoDayServiceDogsExt;

    @Test
    void getReportMaxDateTest() {
        when(dogService.getReportMaxDate()).thenReturn(new ArrayList<Report>());
        notReportsTwoDayServiceDogsExt.getReportMaxDate();
        verify(dogService, times(1)).getReportMaxDate();
    }
}