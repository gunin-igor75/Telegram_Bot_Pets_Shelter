package pro.sky.telegram_bot_pets_shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;
import pro.sky.telegram_bot_pets_shelter.service.imp.DogServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers= DogController.class)
public class DogControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DogController dogController;

    @MockBean
    private DogRepository dogRepository;

    @SpyBean
    private DogServiceImpl dogService;

    @Test
    public void findDogTest() throws Exception {
        Dog dog = Dog.builder()
                .id(10L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();

        when(dogRepository.findById(eq(10L))).thenReturn(Optional.of(dog));
    }

    @Test
    public void editDogTest() throws Exception {
        Dog dog = Dog.builder()
                .id(10L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();
        Dog editDog = Dog.builder()
                .id(10L)
                .name("ray")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();

        when(dogRepository.findById(eq(10L))).thenReturn(Optional.of(dog));
        when(dogRepository.save(any(Dog.class))).thenReturn(editDog);
    }

    @Test
    public void deleteDogTest() throws Exception {
        Dog dog = Dog.builder()
                .id(10L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();

        when(dogRepository.findById(eq(10L))).thenReturn(Optional.of(dog));
        dogService.deleteDog(10L);
        verify(dogRepository, times(1)).delete(dog);
    }

    @Test
    public void getAllDogsTest() throws Exception {
        Dog dog1 = Dog.builder()
                .id(10L)
                .name("ray")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();
        Dog dog2 = Dog.builder()
                .id(11L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDateTime.now())
                .build();

        when(dogRepository.findAll()).thenReturn(List.of(dog1, dog2));
    }
}