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
import pro.sky.telegram_bot_pets_shelter.entity.Cat;
import pro.sky.telegram_bot_pets_shelter.repositories.CatRepository;
import pro.sky.telegram_bot_pets_shelter.service.imp.CatServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers= CatController.class)
public class CatControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatRepository catRepository;

    @SpyBean
    private CatServiceImpl catService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCatTest() throws Exception {
        long id = 10L;
        String name = "kisa";

        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);

        JSONObject catObj = new JSONObject();
        catObj.put("id", id);
        catObj.put("name", name);

        when(catService.findCat(id)).thenReturn(null);
        when(catRepository.save(cat)).thenReturn(cat);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cat")
                        .content(catObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void findCatTest() throws Exception {
        Long id = 10L;
        String name = "kisa";
        boolean adopted = false;
        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);
        cat.setAdopted(adopted);

        when(catRepository.findById(eq(10L))).thenReturn(Optional.of(cat));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cat/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(adopted));
    }

    @Test
    public void editCatTest() throws Exception {
        Long id = 10L;
        String name = "kisa";
        boolean adopted = false;
        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);
        cat.setAdopted(adopted);

        LocalDate dateAdoption = LocalDate.of(2019, 2, 22);
        boolean newAdopted = true;

        Cat newCat = new Cat();
        newCat.setId(id);
        newCat.setName(name);
        newCat.setAdopted(newAdopted);
        newCat.setAdopted(newAdopted);
        newCat.setDateAdoption(dateAdoption);

        JSONObject catObj = new JSONObject();
        catObj.put("id", id);
        catObj.put("name", name);
        catObj.put("adopted", newAdopted);
        catObj.put("dateAdoption", dateAdoption);

        when(catRepository.findById(eq(10L))).thenReturn(Optional.of(cat));
        when(catRepository.save(any(Cat.class))).thenReturn(newCat);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cat")
                        .content(catObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(newAdopted))
                .andExpect(jsonPath("$.dateAdoption").value(String.valueOf(dateAdoption)));
    }

    @Test
    public void deleteCatTest() throws Exception {
        long id = 10L;
        String name = "kisa";
        boolean adopted = true;
        LocalDate dateAdoption = LocalDate.of(2000, 1, 5);

        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);
        cat.setAdopted(adopted);
        cat.setDateAdoption(dateAdoption);

        when(catService.findCat(id)).thenReturn(cat);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cat/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.adopted").value(adopted))
                .andExpect(jsonPath("$.dateAdoption").value(String.valueOf(dateAdoption)));

        verify(catRepository, atLeastOnce()).delete(cat);
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
                .id(20L)
                .name("murzik")
                .adopted(true)
                .dateAdoption(LocalDate.now())
                .build();

        when(catRepository.findAll()).thenReturn(List.of(cat1, cat2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(List.of(cat1, cat2))));
    }
}
