package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.*;
import pro.sky.telegram_bot_pets_shelter.service.*;

import java.util.List;

@Service
public class NotReportsServiceCatsImp extends NotReportsService {
    private final CatService catService;

    public NotReportsServiceCatsImp(OwnerService ownerService, BlackListService blackListService,
                                    VolunteerService volunteerService, TaskService taskService,
                                    CatService catService) {
        super(ownerService, blackListService, volunteerService, taskService);
        this.catService = catService;
    }

    public List<Report> getReportMaxDate() {
        return catService.getReportMaxDate();
    }
}
