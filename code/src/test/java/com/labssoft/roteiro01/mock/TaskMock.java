package com.labssoft.roteiro01.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

public class TaskMock {

    public static List<Task> createTasks() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Task task = TaskMock.createTask(i);

            taskList.add(task);
        }

        return taskList;
    }

    public static Task createTask(int id) {
        return new Task(
                "Title" + id,
                "Description" + id,
                TaskStatus.InProgress,
                TaskType.Free, null, null, null);
    }

    public static Task newTask() {
        return new Task(
                null, "Title",
                "Description",
                TaskStatus.InProgress,
                TaskType.Free, null, null, null);
    }

    public static CreateTask newCreateTask() {
        return new CreateTask(
                "Title",
                "Description",
                TaskType.Free,
                null, null, null);
    }

    public static Date daysBeforeToday(int i) {
        return new Date(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000);
    }

    public static Task newCompleteTask() {
        return new Task(
                null, "Title",
                "Description",
                TaskStatus.Completed,
                TaskType.Free, null, null, null);
    }
}