package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class BotServiceImp {

    private final CatService catService;

    private final MessageUtils messageUtils;
    public BotServiceImp(CatService catService, MessageUtils messageUtils) {
        this.catService = catService;
        this.messageUtils = messageUtils;
    }


    public List<Report> getReportMaxDate() {
        List<Cat> catsAdopted = catService.getCatsByAdoptedIsFalse(LocalDate.now());
        List<Report> reports = new ArrayList<>();
        for (Cat cat : catsAdopted) {
            Report report = cat.getReport()
                    .stream()
                    .max(Comparator.comparing(Report::getDateReport)).orElse(null);
            assert report != null;
            reports.add(report);
        }
        return reports;
    }

    public List<Long> getChatIdBadOwner(List<Report> reports) {
        return reports.stream()
                .filter(e -> e.getDateReport().isBefore(LocalDate.now()))
                .map(Report::getChatId)
                .collect(Collectors.toList());
    }
}
