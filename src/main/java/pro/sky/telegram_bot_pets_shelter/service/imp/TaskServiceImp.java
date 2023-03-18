package pro.sky.telegram_bot_pets_shelter.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.telegram_bot_pets_shelter.entity.Task;
import pro.sky.telegram_bot_pets_shelter.exception_handling.TaskNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.TaskRepository;
import pro.sky.telegram_bot_pets_shelter.service.TaskService;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task createTask(Task task) {
        checkTaskNull(task);
        if (task.getId() == null) {
            return taskRepository.save(task);
        }
        return task;
    }

    @Override
    public Task findTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task editTask(Task task) {
        checkTaskNull(task);
        Task persistentTask = findTask(task.getId());
        if (persistentTask == null) {
            throw new TaskNotFoundException();
        }
        return taskRepository.save(persistentTask);
    }

    @Override
    public Task deleteTask(Long id) {
        Task task = findTask(id);
        if (task == null) {
            throw new TaskNotFoundException();
        }
        taskRepository.delete(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    private void checkTaskNull(Task task) {
        if (task == null) {
            log.error("task is null");
            throw new NullPointerException();
        }
    }
}
