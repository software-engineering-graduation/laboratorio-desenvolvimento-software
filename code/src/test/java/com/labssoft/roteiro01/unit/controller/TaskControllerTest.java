package com.labssoft.roteiro01.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labssoft.roteiro01.controller.TaskController;
import com.labssoft.roteiro01.dto.CreateTask;
import com.labssoft.roteiro01.dto.ReadTask;
import com.labssoft.roteiro01.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.exceptions.InvalidFieldFormatException;
import com.labssoft.roteiro01.mock.TaskMock;
import com.labssoft.roteiro01.repository.TaskRepository;
import com.labssoft.roteiro01.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    TaskService taskService;
    private TaskController sut;

    @BeforeEach
    void setUp() {
        sut = new TaskController(taskService);
    }

    @Test
    @DisplayName("Should return all tasks")
    void should_list_all_tasks() throws Exception {
        // Arrange
        Iterable<ReadTask> tasks = TaskMock.createReadTasks();
        Mockito.when(taskService.listAll()).thenReturn(tasks);

        // Act
        ResponseEntity<Iterable<ReadTask>> result = sut.listAll();

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertEquals(5, result.getBody().spliterator().getExactSizeIfKnown());
    }

    @Test
    @DisplayName("Should return empty list when no tasks")
    void should_return_empty_list_when_no_tasks() {
        // Arrange
        Mockito.when(taskService.listAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<Iterable<ReadTask>> result = sut.listAll();

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertEquals(0, result.getBody().spliterator().getExactSizeIfKnown());
    }

    @Test
    @DisplayName("Should return task by ID")
    void should_return_task_by_id() {
        // Arrange
        ReadTask readTask = TaskMock.createReadTask(1);
        Mockito.when(taskService.findById(anyLong())).thenReturn(readTask);

        // Act
        ResponseEntity<ReadTask> result = sut.findById(1L);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals("Title1", result.getBody().getTitle());
        assertEquals("Description1", result.getBody().getDescription());
        assertEquals(TaskType.Free, result.getBody().getType());
        assertEquals(TaskStatus.InProgress, result.getBody().getStatus());
        assertEquals("InProgress", result.getBody().getStatusDescription());
        assertEquals(null, result.getBody().getPriority());
    }

    @Test
    @DisplayName("Should return 404 when task not found by ID")
    void should_return_404_when_task_not_found_by_id() {
        // Arrange
        Mockito.when(taskService.findById(anyLong())).thenReturn(null);

        // Act
        ResponseEntity<ReadTask> result = sut.findById(1L);

        // Assert
        assertEquals(404, result.getStatusCode().value());
        assertEquals(null, result.getBody());
    }

    @Test
    @DisplayName("Should create a new task")
    void should_create_a_new_task() throws InvalidFieldFormatException {
        // Arrange
        CreateTask createTask = TaskMock.newCreateTask();
        ReadTask readTask = TaskMock.createReadTask(1);
        Mockito.when(taskService.create(any(CreateTask.class))).thenReturn(readTask);

        // Act
        ResponseEntity<ReadTask> result = sut.create(createTask);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals("Title1", result.getBody().getTitle());
        assertEquals("Description1", result.getBody().getDescription());
        assertEquals(TaskType.Free, result.getBody().getType());
        assertEquals(TaskStatus.InProgress, result.getBody().getStatus());
        assertEquals("InProgress", result.getBody().getStatusDescription());
        assertEquals(null, result.getBody().getPriority());
    }

    @Test
    @DisplayName("Should delete a task by ID")
    void should_delete_task_by_id() {
        // Arrange
        Mockito.when(taskService.delete(anyLong())).thenReturn(true);

        // Act
        ResponseEntity<Void> result = sut.delete(1L);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(null, result.getBody());
    }

    @Test
    @DisplayName("Should return 404 when trying to delete non-existing task")
    void should_return_404_when_trying_to_delete_non_existing_task() {
        // Arrange
        Mockito.when(taskService.delete(anyLong())).thenReturn(false);

        // Act
        ResponseEntity<Void> result = sut.delete(1L);

        // Assert
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should complete a task by ID")
    void should_complete_task_by_id() {
        // Arrange
        ReadTask readTask = TaskMock.readCompletedTask();
        Mockito.when(taskService.complete(anyLong())).thenReturn(readTask);

        // Act
        ResponseEntity<ReadTask> result = sut.done(1L);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(TaskStatus.Completed, result.getBody().getStatus());
        assertEquals("Completed", result.getBody().getStatusDescription());
    }

    @Test
    @DisplayName("Should return 404 when trying to complete non-existing task")
    void should_return_404_when_trying_to_complete_non_existing_task() {
        // Arrange
        Mockito.when(taskService.complete(anyLong())).thenReturn(null);

        // Act
        ResponseEntity<ReadTask> result = sut.done(1L);

        // Assert
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    @DisplayName("Should edit a task by ID")
    void should_edit_task_by_id() {
        // Arrange
        UpdateTask updateTask = TaskMock.newUpdateTask();
        Mockito.when(taskService.edit(anyLong(), any(UpdateTask.class))).thenReturn(new ReadTask(
                1L,
                "Updated Task",
                "Updated Description",
                TaskType.Free,
                TaskStatus.Completed,
                "Completed",
                TaskPriority.Medium
        ));

        // Act
        ResponseEntity<ReadTask> result = sut.edit(updateTask, 1L);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals("Updated Task", result.getBody().getTitle());
        assertEquals("Updated Description", result.getBody().getDescription());
        assertEquals(TaskType.Free, result.getBody().getType());
        assertEquals(TaskStatus.Completed, result.getBody().getStatus());
        assertEquals("Completed", result.getBody().getStatusDescription());
    }

    @Test
    @DisplayName("Should return 404 when trying to edit non-existing task")
    void should_return_404_when_trying_to_edit_non_existing_task() {
        // Arrange
        UpdateTask updateTask = TaskMock.newUpdateTask();
        Mockito.when(taskService.edit(anyLong(), any(UpdateTask.class))).thenReturn(null);

        // Act
        ResponseEntity<ReadTask> result = sut.edit(updateTask, 1L);

        // Assert
        assertEquals(404, result.getStatusCode().value());
        assertEquals(null, result.getBody());
    }
}