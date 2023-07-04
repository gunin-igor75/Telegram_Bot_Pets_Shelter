package pro.sky.telegram_bot_pets_shelter.service.imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegram_bot_pets_shelter.exception_handling.TaskNotFoundException;
import pro.sky.telegram_bot_pets_shelter.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static pro.sky.telegram_bot_pets_shelter.Value.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImpTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImp taskServiceImp;
    @Test
    void createTaskTest() {
        when(taskRepository.save(taskFirst)).thenReturn(taskSecond);
        assertThat(taskServiceImp.createTask(taskFirst)).isEqualTo(taskSecond);
        assertThat(taskServiceImp.createTask(taskSecond)).isEqualTo(taskSecond);
        assertThatThrownBy(() -> taskServiceImp.createTask(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void findTaskTes() {
        when(taskRepository.findById(200L)).thenReturn(Optional.of(taskSecond));
        assertThat(taskServiceImp.findTask(200L)).isEqualTo(taskSecond);
        assertThat(taskServiceImp.findTask(1L)).isNotEqualTo(taskFirst);
        assertThat(taskServiceImp.findTask(null)).isEqualTo(null);
    }

    @Test
    void editTaskTest() {
        when(taskRepository.findById(200L)).thenReturn(Optional.ofNullable(taskSecond));
        when(taskRepository.save(taskSecond)).thenReturn(taskSecond);
        assertThat(taskServiceImp.editTask(taskSecond)).isEqualTo(taskSecond);
        assertThatThrownBy(() -> taskServiceImp.editTask(taskFirst))
                .isInstanceOf(TaskNotFoundException.class);
        assertThatThrownBy(() -> taskServiceImp.editTask(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deleteTaskTest() {
        when(taskRepository.findById(200L)).thenReturn(Optional.ofNullable(taskSecond));
        taskServiceImp.deleteTask(200L);
        verify(taskRepository, atLeastOnce()).delete(taskSecond);
        assertThatThrownBy(() -> taskServiceImp.deleteTask(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void getAllTaskTest() {
        when(taskRepository.findAll()).thenReturn(List.of(taskFirst, taskSecond));
        assertThat(taskServiceImp.getAllTasks()).isEqualTo(List.of(taskFirst, taskSecond));
    }
}