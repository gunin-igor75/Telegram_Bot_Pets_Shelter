package pro.sky.telegram_bot_pets_shelter.service;

import pro.sky.telegram_bot_pets_shelter.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    Task findTask(Long id);

    Task editTask(Task task);

    Task deleteTask(Long id);

    List<Task> getAllTasks();
}
