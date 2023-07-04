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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void createDogTest() throws Exception {
        long id = 10L;
        String name = "sharick";

        Dog dog = new Dog();
        dog.setId(id);
        dog.setName(name);

        JSONObject dogObj = new JSONObject();
        dogObj.put("name", name);

        when(dogService.findDog(id)).thenReturn(null);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog")
                        .content(dogObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void deleteDogTest() throws Exception {
        long id = 10L;
        String name = "sharick";
        boolean adopted = true;
        LocalDate dateAdoption = LocalDate.of(2000, 1, 5);

        Dog dog = new Dog();
        dog.setId(id);
        dog.setName(name);
        dog.setAdopted(adopted);
        dog.setDateAdoption(dateAdoption);

        when(dogService.findDog(id)).thenReturn(dog);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(adopted))
                .andExpect(jsonPath("$.dateAdoption").value(String.valueOf(dateAdoption)));

        verify(dogRepository, atLeastOnce()).delete(dog);
    }

    @Test
    public void getAllDogsTest() throws Exception {
        Dog dogFirst = Dog.builder()
                .id(10L)
                .adopted(true)
                .name("pupsick")
                .dateAdoption(LocalDate.now())
                .build();

        Dog dogSecond = Dog.builder()
                .id(20L)
                .adopted(true)
                .name("bim")
                .dateAdoption(LocalDate.now())
                .build();

        when(dogRepository.findAll()).thenReturn(List.of(dogFirst, dogSecond));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(List.of(dogFirst, dogSecond))));
    }
}