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
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;
import pro.sky.telegram_bot_pets_shelter.repositories.VolunteerRepository;
import pro.sky.telegram_bot_pets_shelter.service.imp.VolunteerServiceImp;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VolunteerController.class)
class VolunteerControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerRepository volunteerRepository;

    @SpyBean
    private VolunteerServiceImp volunteerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findVolunteerTest() throws Exception {
        Long id = 10L;
        String username = "Igor";
        long chatId = 11L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setUsername(username);
        volunteer.setChatId(chatId);

        when(volunteerRepository.findById(10L)).thenReturn(Optional.of(volunteer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.chatId").value(chatId));
    }

    @Test
    public void editVolunteerTest() throws Exception {
        Long id = 10L;
        String username = "Igor";
        long chatId = 11L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setUsername(username);
        volunteer.setChatId(chatId);

        Volunteer newVolunteer = new Volunteer();
        String newUsername = "Oleg";
        newVolunteer.setId(id);
        newVolunteer.setUsername(newUsername);
        newVolunteer.setChatId(chatId);

        JSONObject volunteerObj = new JSONObject();
        volunteerObj.put("id", id);
        volunteerObj.put("username", newUsername);
        volunteerObj.put("chatId", chatId);

        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer));
        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(newVolunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer")
                        .content(volunteerObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(newUsername))
                .andExpect(jsonPath("$.chatId").value(chatId));
    }

    @Test
    public void createVolunteerTest() throws Exception {
        Long id = 10L;
        String username = "Igor";
        long chatId = 11L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setUsername(username);
        volunteer.setChatId(chatId);

        JSONObject volunteerObj = new JSONObject();
        volunteerObj.put("username", username);
        volunteerObj.put("chatId", chatId);


        when(volunteerRepository.save(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer")
                        .content(volunteerObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    public void deleteDogTest() throws Exception {
        Long id = 10L;
        String username = "Igor";
        long chatId = 11L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setUsername(username);
        volunteer.setChatId(chatId);

        when(volunteerService.findVolunteer(id)).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.chatId").value(chatId));

        verify(volunteerRepository, atLeastOnce()).delete(volunteer);
    }

    @Test
    public void getAllDogsTest() throws Exception {
        Volunteer volunteerFirst = Volunteer.builder()
                .id(10L)
                .username("pupsick")
                .chatId(11L)
                .build();

        Volunteer volunteerSecond = Volunteer.builder()
                .id(20L)
                .username("bim")
                .chatId(20L)
                .build();

        when(volunteerRepository.findAll()).thenReturn(List.of(volunteerFirst, volunteerSecond));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(List.of(volunteerFirst, volunteerSecond))));
    }
}