package com.labssoft.roteiro01.unit.dto;

import com.labssoft.roteiro01.dto.CreateTask;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateTaskTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Valid CreateTask instance should not have any violations")
    void shouldNotHaveViolationsForValidCreateTask() throws Exception {
        // Arrange
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dueDate = dateFormat.parse("05/04/2024");

        CreateTask createTask = new CreateTask(
                "Valid Title",
                "Valid Description",
                TaskType.Date,
                dueDate,
                5,
                TaskPriority.High
        );

        // Act
        Set<ConstraintViolation<CreateTask>> violations = validator.validate(createTask);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Title shorter than 1 character should have violations")
    void shouldHaveViolationsForShortTitle() {
        // Arrange
        CreateTask createTask = new CreateTask(
                "",
                "Valid Description",
                TaskType.Date,
                new Date(),
                5,
                TaskPriority.High
        );

        // Act
        Set<ConstraintViolation<CreateTask>> violations = validator.validate(createTask);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Title longer than 100 characters should have violations")
    void shouldHaveViolationsForLongTitle() {
        // Arrange
        String longTitle = "a".repeat(101);
        CreateTask createTask = new CreateTask(
                longTitle,
                "Valid Description",
                TaskType.Date,
                new Date(),
                5,
                TaskPriority.High
        );

        // Act
        Set<ConstraintViolation<CreateTask>> violations = validator.validate(createTask);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Description longer than 5000 characters should have violations")
    void shouldHaveViolationsForLongDescription() {
        // Arrange
        String longDescription = "a".repeat(5001);
        CreateTask createTask = new CreateTask(
                "Valid Title",
                longDescription,
                TaskType.Date,
                new Date(),
                5,
                TaskPriority.High
        );

        // Act
        Set<ConstraintViolation<CreateTask>> violations = validator.validate(createTask);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Descrição da tarefa deve possuir no máximo 5000 caracteres", violations.iterator().next().getMessage());
    }
}