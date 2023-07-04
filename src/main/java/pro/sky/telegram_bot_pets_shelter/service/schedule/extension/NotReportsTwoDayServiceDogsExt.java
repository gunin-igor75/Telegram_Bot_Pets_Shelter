package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.service.schedule.NotReportsTwoDayService;

import java.util.List;

@Service
public class NotReportsTwoDayServiceDogsExt extends NotReportsTwoDayService {
    private final DogService dogService;

    public NotReportsTwoDayServiceDogsExt(OwnerService ownerService, BlackListService blackListService,
                                          VolunteerService volunteerService, TaskService taskService,
                                          DogService dogService) {
        super(ownerService, blackListService, volunteerService, taskService);
        this.dogService = dogService;
    }

    public List<Report> getReportMaxDate() {
        return dogService.getReportMaxDate();
    }
}
