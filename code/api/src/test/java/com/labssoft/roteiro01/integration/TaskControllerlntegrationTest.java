package com.labssoft.roteiro01.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.patch;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labssoft.roteiro01.Roteiro01Application;
import com.labssoft.roteiro01.dto.CreateTask;
import com.labssoft.roteiro01.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.mock.TaskMock;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { Roteiro01Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskControllerlntegrationTest {
        @BeforeAll
        public static void setup() {
                RestAssured.baseURI = "http://localhost:8080";
                RestAssured.port = 8080;
        }

        @Test
        void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
                get("/api/task").then().statusCode(200)
                                .assertThat().body("data.size()", equalTo(0));
        }

        @Test
        void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
                // Prepare
                CreateTask task = new CreateTask("New Task", "This is a new task.", TaskType.Free, null, null,
                                TaskPriority.Low);

                given()
                                .contentType(ContentType.JSON)
                                .body(task)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(201)
                                .body("statusDescription", equalTo(TaskStatus.InProgress.toString()));

                // Assert
                get("/api/task/1").then().statusCode(200);
        }

        @Test
        void givenTask_whenPostTask_thenCreated() throws JsonProcessingException {
                // Act
                CreateTask task = new CreateTask("New Task",
                                "This is a new task.",
                                TaskType.Free,
                                null,
                                null,
                                TaskPriority.Low);
                ObjectMapper mapper = new ObjectMapper();
                String jsonTask = mapper.writeValueAsString(task);

                // Act/Assert
                given()
                                .contentType(ContentType.JSON)
                                .body(jsonTask)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(201)
                                .body("id", equalTo(1))
                                .body("title", equalTo("New Task"))
                                .body("description", equalTo("This is a new task."))
                                .body("type", equalTo(TaskType.Free.toString()))
                                .body("status", equalTo(TaskStatus.InProgress.toString()))
                                .body("statusDescription", equalTo(TaskStatus.InProgress.toString()))
                                .body("priority", equalTo(TaskPriority.Low.toString()));
        }

        @Test
        void givenTask_whenDeleteTask_thenOk() {
                // Prepare
                CreateTask task = new CreateTask("New Task", "This is a new task.", TaskType.Free, null, null,
                                TaskPriority.Low);
                given()
                                .contentType(ContentType.JSON)
                                .body(task)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(201);

                // Act/Assert
                delete("/api/task/1")
                                .then()
                                .statusCode(200);
        }

        @Test
        void givenTask_whenCompleteTask_thenOk() {
                // Prepare
                CreateTask task = new CreateTask("New Task", "This is a new task.", TaskType.Free, null, null,
                                TaskPriority.Low);
                given()
                                .contentType(ContentType.JSON)
                                .body(task)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(201)
                                .body("statusDescription", equalTo(TaskStatus.InProgress.toString()));

                // Act/Assert
                patch("/api/task/1/done")
                                .then()
                                .statusCode(200)
                                .body("status", equalTo(TaskStatus.Completed.toString()));
        }

        @Test
        void givenTask_whenUpdateTask_thenOk() throws JsonProcessingException {
                // Prepare
                CreateTask task = new CreateTask("New Task", "This is a new task.", TaskType.Free, null, null,
                                TaskPriority.Low);
                given()
                                .contentType(ContentType.JSON)
                                .body(task)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(201);

                // Act/Assert
                UpdateTask updateTask = new UpdateTask("Updated Task", "This is an updated task.", TaskStatus.Completed,
                                TaskPriority.High);
                given()
                                .contentType(ContentType.JSON)
                                .body(updateTask)
                                .when()
                                .put("/api/task/1")
                                .then()
                                .statusCode(200)
                                .body("title", equalTo("Updated Task"))
                                .body("description", equalTo("This is an updated task."))
                                .body("status", equalTo(TaskStatus.Completed.toString()))
                                .body("priority", equalTo(TaskPriority.High.toString()));
        }

        @Test
        void givenInvalidFieldFormatException_whenThrown_thenHandled() throws JsonProcessingException {
                // Act
                CreateTask task = new CreateTask("New Task",
                                "This is a new task.",
                                TaskType.Date,
                                TaskMock.daysBeforeToday(1),
                                null,
                                TaskPriority.Low);

                // Act/Assert
                given()
                                .contentType(ContentType.JSON)
                                .body(task)
                                .when()
                                .post("/api/task")
                                .then()
                                .statusCode(400);
        }
}