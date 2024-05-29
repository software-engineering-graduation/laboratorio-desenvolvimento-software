package com.labssoft.roteiro01.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.labssoft.roteiro01.dto.CreateTask;
import com.labssoft.roteiro01.dto.ReadTask;
import com.labssoft.roteiro01.dto.UpdateTask;
import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

public class TaskMock {

    public static List<Task> createTasks() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Task task = TaskMock.createTask(i);
            if(i == 3) {
                task.setStatus(TaskStatus.Completed);
            }
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

    public static Task newDateTask() {
        return new Task(
                null, "Title",
                "Description",
                TaskStatus.InProgress,
                TaskType.Date,
                daysBeforeToday(1), null, null);
    }

    public static Task newPeriodTask() {
        return new Task(
                "Title",
                "Description",
                TaskType.Period,
                daysAfterToday(1),
                null);
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

    public static ReadTask readCompletedTask() {
        return new ReadTask(
                1L,
                "Title",
                "Description",
                TaskType.Free,
                TaskStatus.Completed,
                "Completed",
                null);
    }

    public static Date daysAfterToday(int i) {
        return new Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000);
    }

    public static Iterable<ReadTask> createReadTasks() {
        List<ReadTask> readTasks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ReadTask readTask = TaskMock.createReadTask(i);

            readTasks.add(readTask);
        }

        return readTasks;
    }

    public static ReadTask createReadTask(int i) {
        return new ReadTask(
                (long) i,
                "Title" + i,
                "Description" + i,
                TaskType.Free,
                TaskStatus.InProgress,
                "InProgress",
                null);
    }

	public static UpdateTask newUpdateTask() {
        return new UpdateTask(
            "Updated Title",
            "Updated Description",
            TaskStatus.Completed,
            TaskPriority.Medium);
	}
}