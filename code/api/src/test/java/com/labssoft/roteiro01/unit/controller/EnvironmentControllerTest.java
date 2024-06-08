package com.labssoft.roteiro01.unit.controller;

import com.labssoft.roteiro01.controller.EnvironmentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class EnvironmentControllerTest {

    @Mock
    private Environment environment;

    private EnvironmentController sut;

    @BeforeEach
    void setUp() {
        sut = new EnvironmentController(environment);
    }

    @Test
    @DisplayName("Should return environment name")
    void should_return_environment_name() {
        // Arrange
        String expectedEnvironmentName = "test";
        Mockito.when(environment.getProperty("environment.name")).thenReturn(expectedEnvironmentName);

        // Act
        String result = sut.getEnvironmentName();

        // Assert
        assertTrue(result.contains("environmentName"));
        assertTrue(result.contains(expectedEnvironmentName));
        assertEquals("{\"environmentName\": \"test\"}", result);
    }

    @Test
    @DisplayName("Should return empty environment name if property not set")
    void should_return_empty_environment_name_if_property_not_set() {
        // Arrange
        Mockito.when(environment.getProperty("environment.name")).thenReturn(null);

        // Act
        String result = sut.getEnvironmentName();

        // Assert
        assertTrue(result.contains("environmentName"));
        assertEquals("{\"environmentName\": \"null\"}", result);
    }
}