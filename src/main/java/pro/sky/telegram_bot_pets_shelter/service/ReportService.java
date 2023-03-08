package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Report;

import java.util.List;

public interface ReportService {
    Report createReport(Report report);
    Report findReport(Long id);
    Report editReport(Report report);
    Report deleteReport(Long id);
    List<Report> getAllReports();
}
