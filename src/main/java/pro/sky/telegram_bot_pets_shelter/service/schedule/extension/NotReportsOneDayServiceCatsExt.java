package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.schedule.NotReportsOneDayService;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.List;

@Service
public class NotReportsOneDayServiceCatsExt extends NotReportsOneDayService {

    private final CatService catService;

    public NotReportsOneDayServiceCatsExt(MessageUtils messageUtils, TelegramBot telegramBot,
                                          CatService catService) {
        super(messageUtils, telegramBot);
        this.catService = catService;
    }

    @Override
    protected List<Report> getReportMaxDate() {
        return catService.getReportMaxDate();
    }
}
