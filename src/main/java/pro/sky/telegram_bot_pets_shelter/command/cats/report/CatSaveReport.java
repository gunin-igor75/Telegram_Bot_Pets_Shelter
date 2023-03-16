package pro.sky.telegram_bot_pets_shelter.command.cats.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.command.general.PetSaveReport;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.CatService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;

@Component
@Slf4j
public class CatSaveReport extends PetSaveReport implements Command {
    private final CatService catService;
    private Cat cat;

    public CatSaveReport(MessageUtils messageUtils, ReportService reportService,
                         OwnerService ownerService, CatService catService) {
        super(messageUtils, reportService, ownerService);
        this.catService = catService;
    }

    public void creteReportPet(long chatId, String fileId, String healthStatus) {
        owner = ownerService.findOwnerByChatId(chatId);
        cat = owner.getCat();
        report = Report.builder()
                .chatId(chatId)
                .fileId(fileId)
                .healthStatus(healthStatus)
                .dateReport(LocalDate.now())
                .build();
        reports = cat.getReport();
        reports.add(report);
        cat.setReport(reports);
        catService.editCat(cat);
        owner.setState(BASIC_STATE);
        ownerService.editOwner(owner);
    }
    public void editReportPet(Report report, String fileIdOrHealthStatus) {
        owner = ownerService.findOwnerByChatId(report.getChatId());
        cat = owner.getCat();
        setFieldOrHealthStatus(report, fileIdOrHealthStatus);
        reports = cat.getReport();
        reports.remove(report);
        reports.add(report);
        cat.setReport(reports);
        catService.editCat(cat);
        owner.setState(BASIC_STATE);
        ownerService.editOwner(owner);
    }
}















