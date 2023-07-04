package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.exception_handling.CatNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class CatServiceImplTest {
    @Mock
    private CatRepository catRepository;

    @InjectMocks
    private CatServiceImpl catService;

    private LocalDate dateReport;

    @Test
    void createCatTest() {
        when(catRepository.save(catFirst)).thenReturn(catSecond);
        assertThat(catService.createCat(catFirst)).isEqualTo(catSecond);
        assertThat(catService.createCat(catSecond)).isEqualTo(catSecond);
        assertThatThrownBy(() -> catService.createCat(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findCatTest() {
        when(catRepository.findById(10L)).thenReturn(Optional.of(cat));
        assertThat(catService.findCat(10L)).isEqualTo(cat);
        assertThat(catService.findCat(11L)).isNotEqualTo(cat);
        assertThat(catService.findCat(null)).isEqualTo(null);
    }

    @Test
    void editCatTest() {
        when(catRepository.findById(10L)).thenReturn(Optional.ofNullable(cat));
        when(catRepository.save(cat)).thenReturn(newCat);
        assertThat(catService.editCat(cat)).isEqualTo(newCat);
        assertThatThrownBy(() -> catService.editCat(catFirst))
                .isInstanceOf(CatNotFoundException.class);
        assertThatThrownBy(() -> catService.createCat(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteCatTest() {
        when(catRepository.findById(10L)).thenReturn(Optional.ofNullable(cat));
        catService.deleteCat(10L);
        verify(catRepository, atLeastOnce()).delete(cat);
        assertThatThrownBy(() -> catService.deleteCat(11L))
                .isInstanceOf(CatNotFoundException.class);
    }

    @Test
    void getAllCatsTest() {
        when(catRepository.findAll()).thenReturn(List.of(catFirst, catSecond));
        assertThat(catService.getAllCats()).isEqualTo(List.of(catFirst, catSecond));
    }

    @Test
    void getAllCatsFreeTest() {
        when(catRepository.findAll()).thenReturn(List.of(catFirst, catSecond));
        assertThat(catService.getAllCats()).isEqualTo(List.of(catFirst, catSecond));
    }

    @Test
    void getReportMaxDateTest() {
        List<Cat> cats = getCats();
        when(catService.getCatsByAdoptedAndDateAdoptionBefore(LocalDate.now())).thenReturn(cats);
        List<Report> actualReports = catService.getReportMaxDate();
        thenReportMaxDate(actualReports);
    }

    private void thenReportMaxDate(List<Report> actualReports) {
        List<Report> expectedReports = List.of(
                getReport(dateReport.minusDays(1), 5),
                getReport(dateReport.minusDays(2), 4)
        );
        assertThat(actualReports).containsExactlyInAnyOrderElementsOf(expectedReports);
        assertThat(actualReports.size()).isEqualTo(2);
    }

    private List<Cat> getCats() {
        dateReport = LocalDate.now();
        Set<Report> reportsFirst = Set.of(
                getReport(dateReport.minusDays(1), 5),
                getReport(dateReport.minusDays(2), 3),
                getReport(dateReport.minusDays(3), 1)
        );
        Cat catFirst = getCat("Mur", 1);
        catFirst.setReport(reportsFirst);
        Set<Report> reportsSecond = Set.of(
                getReport(dateReport.minusDays(2), 4),
                getReport(dateReport.minusDays(3), 2)
        );
        Cat catSecond = getCat("Kisa", 2);
        catSecond.setReport(reportsSecond);
        return List.of(catFirst, catSecond);
    }

    private Cat getCat(String name, long id) {
        return Cat.builder()
                .id(id)
                .adopted(false)
                .build();
    }

    private Report getReport(LocalDate dateReport, long id) {
        return Report.builder()
                .id(id)
                .dateReport(dateReport)
                .chatId(100L)
                .build();
    }
}