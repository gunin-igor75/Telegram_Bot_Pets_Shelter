package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ReportNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.ReportRepository;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(Report report) {
        checkReportNull(report);
        if (report.getId() != null) {
            return report;
        }
        return reportRepository.save(report);
    }

    @Override
    public Report findReportCat(long chatId, LocalDate dateReport) {
        Optional<Report> report = reportRepository.getReportCat(chatId, dateReport);
        return report.orElse(null);
    }

    @Override
    public Report findReportDog(long chatId, LocalDate dateReport) {
        return reportRepository.getReportDog(chatId, dateReport);
    }
    @Override
    public Report findReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Report editReport(Report report) {
        checkReportNull(report);
        Report persistentReport =findReport(report.getId());
        if (persistentReport == null) {
            throw new ReportNotFoundException();
        }
        return reportRepository.save(report);
    }


    @Override
    public Report deleteReport(Long id) {
        Report report = findReport(id);
        if (report == null) {
            throw new ReportNotFoundException();
        }
        reportRepository.delete(report);
        return report;
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public int getCountReportCatClear(long id) {
       return reportRepository.getCountReportCatClear(id);
    }

    @Override
    public int getCountReportDogClear(long id) {
        return reportRepository.getCountReportDogClear(id);
    }

    private void checkReportNull(Report report) {
        if (report == null) {
            log.error("report is null");
            throw new NullPointerException();
        }
    }
}
