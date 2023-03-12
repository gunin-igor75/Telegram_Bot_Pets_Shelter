package pro.sky.telegram_bot_pets_shelter.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;
import pro.sky.telegram_bot_pets_shelter.service.imp.CatServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers= CatController.class)
public class CatControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatRepository catRepository;

    @SpyBean
    private CatServiceImpl catService;


    @InjectMocks
    private CatController catController;

    @Test
    public void findCatTest() throws Exception {
        Cat cat = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(catRepository.findById(eq(10L))).thenReturn(Optional.of(cat));
    }

    @Test
    public void editCatTest() throws Exception {
        Cat cat = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();
        Cat editCat = Cat.builder()
                .id(10L)
                .name("murzik")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(catRepository.findById(eq(10L))).thenReturn(Optional.of(cat));
        when(catRepository.save(any(Cat.class))).thenReturn(editCat);
    }

    @Test
    public void deleteCatTest() throws Exception {
        Cat cat = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(catRepository.findById(eq(10L))).thenReturn(Optional.of(cat));
        catService.deleteCat(10L);
        verify(catRepository, times(1)).delete(cat);
    }

    @Test
    public void getAllCatsTest() throws Exception {
        Cat cat1 = Cat.builder()
                .id(10L)
                .name("kisa")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();
        Cat cat2 = Cat.builder()
                .id(11L)
                .name("murzik")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));
    }
}
