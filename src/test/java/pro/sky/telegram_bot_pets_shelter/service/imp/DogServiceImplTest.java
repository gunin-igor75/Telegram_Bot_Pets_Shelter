package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.entity.Report;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class DogServiceImplTest {
    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogServiceImpl dogService;

    private LocalDate dateReport;


    @Test
    void createDogTest() {
        when(dogRepository.save(dogFirst)).thenReturn(dogSecond);
        assertThat(dogService.createDog(dogFirst)).isEqualTo(dogSecond);
        assertThat(dogService.createDog(dogSecond)).isEqualTo(dogSecond);
        assertThatThrownBy(() -> dogService.createDog(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findDogTes() {
        when(dogRepository.findById(10L)).thenReturn(Optional.of(dog));
        assertThat(dogService.findDog(10L)).isEqualTo(dog);
        assertThat(dogService.findDog(1L)).isNotEqualTo(dog);
        assertThat(dogService.findDog(null)).isEqualTo(null);
    }

    @Test
    void editDogTest() {
        when(dogRepository.findById(10L)).thenReturn(Optional.ofNullable(dog));
        when(dogRepository.save(dog)).thenReturn(newDog);
        assertThat(dogService.editDog(dog)).isEqualTo(newDog);
        assertThatThrownBy(() -> dogService.editDog(dogFirst))
                .isInstanceOf(DogNotFoundException.class);
        assertThatThrownBy(() -> dogService.createDog(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteDogTest() {
        when(dogRepository.findById(10L)).thenReturn(Optional.ofNullable(dog));
        dogService.deleteDog(10L);
        verify(dogRepository, atLeastOnce()).delete(dog);
        assertThatThrownBy(() -> dogService.deleteDog(1L))
                .isInstanceOf(DogNotFoundException.class);
    }

    @Test
    void getAllDogsTest() {
        when(dogRepository.findAll()).thenReturn(List.of(dogFirst, dogSecond));
        assertThat(dogService.getAllDogs()).isEqualTo(List.of(dogFirst, dogSecond));
    }

    @Test
    void getAllDogsFree() {
        when(dogRepository.getDogsByAdoptedIsNull()).thenReturn(List.of(dogFirst, dogSecond));
        assertThat(dogService.getAllDogsFree()).isEqualTo(List.of(dogFirst, dogSecond));
    }

    @Test
    void getReportMaxDateTest() {
        List<Dog> dogs = getDogs();
        when(dogService.getDogsByAdoptedAndDateAdoptionBefore(LocalDate.now())).thenReturn(dogs);
        List<Report> actualReports = dogService.getReportMaxDate();
        thenReportMaxDate(actualReports);
    }

    private void thenReportMaxDate(List<Report> actualReports) {
        List<Report> expectedReports = List.of(
                getreport(dateReport.minusDays(1), 5),
                getreport(dateReport.minusDays(2), 4)
        );
        assertThat(actualReports).containsExactlyInAnyOrderElementsOf(expectedReports);
        assertThat(actualReports.size()).isEqualTo(2);
    }

    private List<Dog> getDogs() {
        dateReport = LocalDate.now();
        Set<Report> reportsFirst = Set.of(
                getreport(dateReport.minusDays(1), 5),
                getreport(dateReport.minusDays(2), 3),
                getreport(dateReport.minusDays(3), 1)
        );
        Dog dogFirst = getDog("Bim", 1);
        dogFirst.setReport(reportsFirst);
        Set<Report> reportsSecond = Set.of(
                getreport(dateReport.minusDays(2), 4),
                getreport(dateReport.minusDays(3), 2)
        );
        Dog dogSecond = getDog("Bam", 2);
        dogSecond.setReport(reportsSecond);
        return List.of(dogFirst, dogSecond);
    }

    private Dog getDog(String name, long id) {
        return Dog.builder()
                .id(id)
                .adopted(false)
                .build();
    }

    private Report getreport(LocalDate dateReport, long id) {
        return Report.builder()
                .id(id)
                .dateReport(dateReport)
                .chatId(100L)
                .build();
    }
}