package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.exception_handling.DogNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;


import java.util.List;
import java.util.Optional;

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
}