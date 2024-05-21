package com.labssoft.roteiro01.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.ReadTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.exceptions.InvalidFieldFormatException;
import com.labssoft.roteiro01.mock.TaskMock;
import com.labssoft.roteiro01.repository.TaskRepository;
import com.labssoft.roteiro01.service.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    TaskRepository tasksRepository;
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        taskService = new TaskService(tasksRepository);
        Mockito.lenient().when(tasksRepository.findAll()).thenReturn(TaskMock.createTasks());
    }

    @Test
    @DisplayName("Should retun all tasks")
    void should_list_all_tasks_repository() {
        Iterable<ReadTask> tasks = taskService.listAll();
        assertNotNull(tasks);
        assertEquals(5, tasks.spliterator().getExactSizeIfKnown());
    }

    @Test
    @DisplayName("Should create a new task")
    void should_create_a_new_task() throws InvalidFieldFormatException {
        // Arrange
        Mockito.lenient().when(tasksRepository.save(Mockito.any())).thenReturn(TaskMock.newTask());
        CreateTask createTask = TaskMock.newCreateTask();

        // Act
        ReadTask createdTask = taskService.create(TaskMock.newCreateTask());

        // Assert
        assertNotNull(createdTask);
        assertEquals(createTask.getTitle(), createdTask.getTitle());
        assertEquals(createTask.getDescription(), createdTask.getDescription());
        assertEquals(createTask.getType(), createdTask.getType());
        assertEquals(TaskStatus.InProgress, createdTask.getStatus());
        assertEquals("InProgress", createdTask.getStatusDescription());
        assertEquals(createTask.getPriority(), createdTask.getPriority());
    }

    @Test
    @DisplayName("Should throw exception when task type is date and due date is before today")
    void should_throw_exception_when_task_type_is_date_and_due_date_is_before_today() {
        // Arrange
        CreateTask createTask = TaskMock.newCreateTask();
        createTask.setType(TaskType.Date);
        createTask.setDueDate(TaskMock.daysBeforeToday(1));

        // Act and Assert
        InvalidFieldFormatException exception = assertThrows(InvalidFieldFormatException.class, () -> {
            taskService.create(createTask);
        });

        assertEquals("Data de vencimento deve ser maior ou igual que a data atual.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when task type is period and due days is less than 1")
    void should_throw_exception_when_task_type_is_period_and_due_days_is_less_than_1() {
        // Arrange
        CreateTask createTask = TaskMock.newCreateTask();
        createTask.setType(TaskType.Period);
        createTask.setDueDays(0);

        // Act and Assert
        InvalidFieldFormatException exception = assertThrows(InvalidFieldFormatException.class, () -> {
            taskService.create(createTask);
        });

        assertEquals("Dias para vencimento deve ser maior ou igual a 1.", exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully delete a task")
    void should_delete_task_by_id() {
        Mockito.lenient().when(tasksRepository.existsById(anyLong())).thenReturn(true);
        Mockito.lenient().doNothing().when(tasksRepository).deleteById(anyLong());

        boolean result = taskService.delete(1L);

        assertTrue(result);
        verify(tasksRepository, times(1)).deleteById(1L);
        verify(tasksRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Should return false when trying to delete a non existing task")
    void should_return_false_when_trying_to_delete_a_non_existing_task() {
        Mockito.lenient().when(tasksRepository.existsById(anyLong())).thenReturn(false);

        boolean result = taskService.delete(1L);

        assertTrue(!result);
        verify(tasksRepository, times(0)).deleteById(1L);
        verify(tasksRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Should complete a task")
    void should_complete_task_by_id() throws InvalidFieldFormatException {
        // Arrange
        Mockito.lenient().when(tasksRepository.save(Mockito.any())).thenReturn(TaskMock.newTask());
        Mockito.lenient().when(tasksRepository.findById(anyLong())).thenReturn(Optional.of(TaskMock.newTask()));
        Mockito.lenient().when(tasksRepository.save(Mockito.any())).thenReturn(TaskMock.newCompleteTask());

        CreateTask createTask = TaskMock.newCreateTask();

        // Act
        ReadTask completedTask = taskService.complete(1L);

        // Assert
        assertNotNull(completedTask);
        assertEquals(createTask.getTitle(), completedTask.getTitle());
        assertEquals(createTask.getDescription(), completedTask.getDescription());
        assertEquals(createTask.getType(), completedTask.getType());
        assertEquals(TaskStatus.Completed, completedTask.getStatus());
        assertEquals("Completed", completedTask.getStatusDescription());
        assertEquals(createTask.getPriority(), completedTask.getPriority());
    }

    @Test
    @DisplayName("Should not complete a task that is already completed")
    void should_not_complete_task_that_is_already_completed() {
        // Arrange
        Mockito.lenient().when(tasksRepository.findById(anyLong())).thenReturn(Optional.of(TaskMock.newCompleteTask()));

        // Act
        ReadTask completedTask = taskService.complete(1L);

        // Assert
        assertEquals(null, completedTask);
        verify(tasksRepository, times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should edit a task")
    void should_edit_task_by_id() {
        Task task = new Task("Old Title", "Old Description", TaskStatus.InProgress, TaskType.Free, null, null, 1);
        UpdateTask updateTask = new UpdateTask("New Title", "New Description", TaskStatus.Completed, 1);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        ReadTask result = taskService.edit(1L, updateTask);

        assertNotNull(result);
        assertEquals("New Title", task.getTitle());
        assertEquals("New Description", task.getDescription());
        assertEquals(TaskStatus.Completed, task.getStatus());
        verify(taskRepository, times(1)).save(task);
    }
}
