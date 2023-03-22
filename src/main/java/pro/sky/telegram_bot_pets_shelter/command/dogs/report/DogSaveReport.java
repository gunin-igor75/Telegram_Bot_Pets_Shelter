package pro.sky.telegram_bot_pets_shelter.command.dogs.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.sky.telegram_bot_pets_shelter.command.Command;
import pro.sky.telegram_bot_pets_shelter.command.general.PetSaveReport;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.service.DogService;
import pro.sky.telegram_bot_pets_shelter.service.OwnerService;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;
import pro.sky.telegram_bot_pets_shelter.utils.MessageUtils;

import java.time.LocalDate;

import static pro.sky.telegram_bot_pets_shelter.service.enums.UserState.BASIC_STATE;
@Component
@Slf4j
public class DogSaveReport extends PetSaveReport implements Command {
    private final DogService dogService;
    private Dog dog;

    public DogSaveReport(MessageUtils messageUtils, ReportService reportService,
                         OwnerService ownerService, DogService dogService) {
        super(messageUtils, reportService, ownerService);
        this.dogService = dogService;
    }

    @Override
    protected Report getReportPet(long chatId, LocalDate dateReport) {
        return reportService.findReportDog(chatId, dateReport);
    }

    public void creteReportPet(long chatId, String fileId, String healthStatus) {
        owner = ownerService.findOwnerByChatId(chatId);
        dog = owner.getDog();
        report = Report.builder()
                .chatId(chatId)
                .fileId(fileId)
                .healthStatus(healthStatus)
                .dateReport(LocalDate.now())
                .build();
        reports = dog.getReport();
        reports.add(report);
        dog.setReport(reports);
        dogService.editDog(dog);
        owner.setState(BASIC_STATE);
        ownerService.editOwner(owner);
    }
    public void editReportPet(Report report, String fileIdOrHealthStatus) {
        owner = ownerService.findOwnerByChatId(report.getChatId());
        dog = owner.getDog();
        setFieldOrHealthStatus(report, fileIdOrHealthStatus);
        reports = dog.getReport();
        reports.remove(report);
        reports.add(report);
        dog.setReport(reports);
        dogService.editDog(dog);
        owner.setState(BASIC_STATE);
        ownerService.editOwner(owner);
    }
}
