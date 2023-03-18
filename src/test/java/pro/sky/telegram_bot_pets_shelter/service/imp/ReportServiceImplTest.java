package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.exception_handling.ReportNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.ReportRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;
    @InjectMocks
    private ReportServiceImpl reportService;
    @Test
    void createReportTest() {
        when(reportRepository.save(reportFirst)).thenReturn(reportSecond);
        assertThat(reportService.createReport(reportFirst)).isEqualTo(reportSecond);
        assertThat(reportService.createReport(reportSecond)).isEqualTo(reportSecond);
        assertThatThrownBy(() -> reportService.createReport(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findReportTes() {
        when(reportRepository.findById(200L)).thenReturn(Optional.of(reportSecond));
        assertThat(reportService.findReport(200L)).isEqualTo(reportSecond);
        assertThat(reportService.findReport(1L)).isNotEqualTo(reportFirst);
        assertThat(reportService.findReport(null)).isEqualTo(null);
    }

    @Test
    void editReportTest() {
        when(reportRepository.findById(200L)).thenReturn(Optional.ofNullable(reportSecond));
        when(reportRepository.save(reportSecond)).thenReturn(reportSecond);
        assertThat(reportService.editReport(reportSecond)).isEqualTo(reportSecond);
        assertThatThrownBy(() -> reportService.editReport(reportFirst))
                .isInstanceOf(ReportNotFoundException.class);
        assertThatThrownBy(() -> reportService.editReport(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteReportTest() {
        when(reportRepository.findById(200L)).thenReturn(Optional.ofNullable(reportSecond));
        reportService.deleteReport(200L);
        verify(reportRepository, atLeastOnce()).delete(reportSecond);
        assertThatThrownBy(() -> reportService.deleteReport(1L))
                .isInstanceOf(ReportNotFoundException.class);
    }

    @Test
    void getAllReportTest() {
        when(reportRepository.findAll()).thenReturn(List.of(reportFirst, reportSecond));
        assertThat(reportService.getAllReports()).isEqualTo(List.of(reportFirst, reportSecond));
    }
}