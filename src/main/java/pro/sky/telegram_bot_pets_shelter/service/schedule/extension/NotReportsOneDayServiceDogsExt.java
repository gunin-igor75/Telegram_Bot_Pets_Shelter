package pro.sky.telegram_bot_pets_shelter.service.schedule.extension;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.controller.TelegramBot;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.schedule.NotReportsOneDayService;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.util.List;

@Service
public class NotReportsOneDayServiceDogsExt extends NotReportsOneDayService {

    private final DogService dogService;

    public NotReportsOneDayServiceDogsExt(MessageUtils messageUtils, TelegramBot telegramBot,
                                          DogService dogService) {
        super(messageUtils, telegramBot);
        this.dogService = dogService;
    }

    @Override
    protected List<Report> getReportMaxDate() {
        return dogService.getReportMaxDate();
    }
}
