package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.exception_handling.VolunteerNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.VolunteerRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceImpTest {
    @Mock
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private VolunteerServiceImp volunteerServiceImp;
    @Test
    void createVolunteerTest() {
        when(volunteerRepository.save(volunteerFirst)).thenReturn(volunteerSecond);
        assertThat(volunteerServiceImp.createVolunteer(volunteerFirst)).isEqualTo(volunteerSecond);
        assertThat(volunteerServiceImp.createVolunteer(volunteerSecond)).isEqualTo(volunteerSecond);
        assertThatThrownBy(() -> volunteerServiceImp.createVolunteer(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findVolunteerTes() {
        when(volunteerRepository.findById(200L)).thenReturn(Optional.of(volunteerSecond));
        assertThat(volunteerServiceImp.findVolunteer(200L)).isEqualTo(volunteerSecond);
        assertThat(volunteerServiceImp.findVolunteer(1L)).isNotEqualTo(volunteerFirst);
        assertThat(volunteerServiceImp.findVolunteer(null)).isEqualTo(null);
    }

    @Test
    void editVolunteerTest() {
        when(volunteerRepository.findById(200L)).thenReturn(Optional.ofNullable(volunteerSecond));
        when(volunteerRepository.save(volunteerSecond)).thenReturn(volunteerSecond);
        assertThat(volunteerServiceImp.editVolunteer(volunteerSecond)).isEqualTo(volunteerSecond);
        assertThatThrownBy(() -> volunteerServiceImp.editVolunteer(volunteerFirst))
                .isInstanceOf(VolunteerNotFoundException.class);
        assertThatThrownBy(() -> volunteerServiceImp.editVolunteer(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteVolunteerTest() {
        when(volunteerRepository.findById(200L)).thenReturn(Optional.ofNullable(volunteerSecond));
        volunteerServiceImp.deleteVolunteer(200L);
        verify(volunteerRepository, atLeastOnce()).delete(volunteerSecond);
        assertThatThrownBy(() -> volunteerServiceImp.deleteVolunteer(1L))
                .isInstanceOf(VolunteerNotFoundException.class);
    }

    @Test
    void getAllVolunteerTest() {
        when(volunteerRepository.findAll()).thenReturn(List.of(volunteerFirst, volunteerSecond));
        assertThat(volunteerServiceImp.getAllVolunteers()).isEqualTo(List.of(volunteerFirst, volunteerSecond));
    }
}