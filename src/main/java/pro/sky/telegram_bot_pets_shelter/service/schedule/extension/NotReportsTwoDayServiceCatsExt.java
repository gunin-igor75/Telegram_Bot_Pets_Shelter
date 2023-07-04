package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.*;
import pro.sky.telegram_bot_pets_shelter.service.*;
import pro.sky.telegram_bot_pets_shelter.service.schedule.NotReportsTwoDayService;

import java.util.List;

@Service
public class NotReportsTwoDayServiceCatsExt extends NotReportsTwoDayService {
    private final CatService catService;

    public NotReportsTwoDayServiceCatsExt(OwnerService ownerService, BlackListService blackListService,
                                          VolunteerService volunteerService, TaskService taskService,
                                          CatService catService) {
        super(ownerService, blackListService, volunteerService, taskService);
        this.catService = catService;
    }

    public List<Report> getReportMaxDate() {
        return catService.getReportMaxDate();
    }
}
