package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegram_bot_pets_shelter.entity.BlackList;
import pro.sky.telegram_bot_pets_shelter.entity.Task;
import pro.sky.telegram_bot_pets_shelter.entity.Volunteer;
import pro.sky.telegram_bot_pets_shelter.repositories.BlackListRepository;
import pro.sky.telegram_bot_pets_shelter.repositories.TaskRepository;
import pro.sky.telegram_bot_pets_shelter.repositories.VolunteerRepository;

@SpringBootTest
class BotServiceImpTest {
    @Autowired
    private  VolunteerRepository volunteerRepository;
    @Autowired
    private  TaskRepository taskRepository;
    @Autowired
    private  BlackListRepository blackListRepository;

    @Test
    public void test() {
        BlackList tom = BlackList.builder()
                .chatId(111L)
                .username("tom")
                .build();
        BlackList transientTom = blackListRepository.save(tom);
        Volunteer pety = Volunteer.builder()
                .chatId(222L)
                .username("pety")
                .build();
        Volunteer transientPety = volunteerRepository.save(pety);
        Task task1 = Task.builder()
                .done(false)
                .blackList(transientTom)
                .volunteer(transientPety)
                .build();
        taskRepository.save(task1);
    }
}


