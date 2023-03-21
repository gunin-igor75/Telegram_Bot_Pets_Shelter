package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.*;

import java.util.List;

@Service
public class NotReportsServiceDogsImp extends NotReportsService {
    private final DogService dogService;

    public NotReportsServiceDogsImp(OwnerService ownerService, BlackListService blackListService,
                                    VolunteerService volunteerService, TaskService taskService,
                                    DogService dogService) {
        super(ownerService, blackListService, volunteerService, taskService);
        this.dogService = dogService;
    }

    public List<Report> getReportMaxDate() {
        return dogService.getReportMaxDate();
    }
}
