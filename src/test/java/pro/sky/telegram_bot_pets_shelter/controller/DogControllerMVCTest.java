package pro.sky.telegram_bot_pets_shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegram_bot_pets_shelter.entity.Dog;
import pro.sky.telegram_bot_pets_shelter.repositories.DogRepository;
import pro.sky.telegram_bot_pets_shelter.service.imp.DogServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DogController.class)
public class DogControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogRepository dogRepository;

    @SpyBean
    private DogServiceImpl dogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findDogTest() throws Exception {
        Long id = 10L;
        String name = "graf";
        boolean adopted = false;
        Dog dog = new Dog();
        dog.setId(id);
        dog.setName(name);
        dog.setAdopted(adopted);

        when(dogRepository.findById(10L)).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(adopted));
    }

    @Test
    public void editDogTest() throws Exception {
        Long id = 10L;
        String name = "graf";
        boolean adopted = false;

        Dog dogOld = new Dog();
        dogOld.setId(id);
        dogOld.setName(name);
        dogOld.setAdopted(adopted);

        LocalDate dateAdoption = LocalDate.of(2019, 2, 22);
        boolean newAdopted = true;

        Dog newDog = new Dog();
        newDog.setId(id);
        newDog.setName(name);
        newDog.setAdopted(newAdopted);
        newDog.setDateAdoption(dateAdoption);

        JSONObject dogObj = new JSONObject();
        dogObj.put("id", id);
        dogObj.put("name", name);
        dogObj.put("adopted", newAdopted);
        dogObj.put("dateAdoption", dateAdoption);

        when(dogRepository.findById(10L)).thenReturn(Optional.of(dogOld));
        when(dogRepository.save(any(Dog.class))).thenReturn(newDog);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog")
                        .content(dogObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(newAdopted))
                .andExpect(jsonPath("$.dateAdoption").value(String.valueOf(dateAdoption)));
    }

    @Test
    public void deleteDogTest() throws Exception {
        Dog dog = Dog.builder()
                .id(10L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDate.now())
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
                .dateAdoption(LocalDate.now())
                .build();
        Dog dog2 = Dog.builder()
                .id(11L)
                .name("graf")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(dogRepository.findAll()).thenReturn(List.of(dog1, dog2));
    }
}