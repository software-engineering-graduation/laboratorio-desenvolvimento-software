package com.labssoft.roteiro01.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { Roteiro01Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class TaskControllerlntegrationTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/tasks").then().statusCode(404)
                .assertThat().body("error", equalTo("Not Found"))
                .assertThat().body("status", equalTo(404))
                .assertThat().body("path", equalTo("/api/tasks"));
    }

    @Test
    void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        get("/api/tasks/1").then().statusCode(404)
                .assertThat().body("error", equalTo("Not Found"))
                .assertThat().body("status", equalTo(404))
                .assertThat().body("path", equalTo("/api/tasks/1"));
    }

    @Test
    void givenTask_whenPostTask_thenCreated() throws JsonProcessingException {
        CreateTask task = new CreateTask("New Task", "This is a new task.", TaskType.Free, null, null,
                TaskPriority.Low);
        ObjectMapper mapper = new ObjectMapper();
        String jsonTask = mapper.writeValueAsString(task);

        given()
                .contentType(ContentType.JSON)
                .body(jsonTask)
                .when()
                .post("/api/task")
                .then()
                .statusCode(201)
                .body("title", equalTo("New Task"))
                .body("description", equalTo("This is a new task."));
    }

    @Test
    void givenTask_whenDeleteTask_thenOk() {
        // Assuming a task with ID 1 exists
        delete("/api/task/1")
                .then()
                .statusCode(200);
    }

    @Test
    void givenTask_whenCompleteTask_thenOk() {
        // Assuming a task with ID 1 exists
        patch("/api/task/1/done").then().statusCode(200)
                .body("status", equalTo("DONE"));
    }

    @Test
    void givenTask_whenUpdateTask_thenOk() throws JsonProcessingException {
        UpdateTask task = new UpdateTask("Updated Task", "This is an updated task.", TaskStatus.InProgress);
        ObjectMapper mapper = new ObjectMapper();
        String jsonTask = mapper.writeValueAsString(task);

        given()
                .contentType(ContentType.JSON)
                .body(jsonTask)
                .when()
                .put("/api/task/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Task"))
                .body("description", equalTo("This is an updated task."))
                .body("status", equalTo("IN_PROGRESS"));
    }
}