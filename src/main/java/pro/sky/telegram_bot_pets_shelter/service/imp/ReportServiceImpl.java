package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ReportNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.ReportRepository;
import pro.sky.telegram_bot_pets_shelter.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(Report report) {
        if (report.getId() != null) {
            return report;
        }
        return reportRepository.save(report);
    }

    public Report findReportCat(long chatId, LocalDate date) {
        return null;
    }
    @Override
    public Report findReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Report editReport(Report report) {
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
}
