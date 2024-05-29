package com.labssoft.roteiro01.mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.labssoft.roteiro01.dto.ReadTask;
import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;
import com.labssoft.roteiro01.util.DateCompare;

public class TaskReadMapper {
    private TaskReadMapper() {
    }
    
    public static ReadTask mapToReadTask(Task task) {
        return new ReadTask(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getType(),
                task.getStatus(),
                calculateStatusDescription(task.getType(), task.getDueDate(), task.getDueDays(), task.getStatus()),
                task.getPriority());
    }

    public static List<ReadTask> mapToReadTaskList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskReadMapper::mapToReadTask)
                .collect(Collectors.toList());
    }

    private static String calculateStatusDescription(TaskType type, Date dueDate, Integer dueDays, TaskStatus status) {
        if (type.equals(TaskType.Free) || status.equals(TaskStatus.Completed)) {
            return status.toString();
        }

        return dateOrPeriodTypeStatusDescription(dueDate, status);
    }

    private static String dateOrPeriodTypeStatusDescription(Date dueDate, TaskStatus status) {
        if (DateCompare.isPastDueDate(dueDate)) {
            return new StringBuilder("Task is ")
                    .append(DateCompare.daysPastDueDate(dueDate))
                    .append(" days late")
                    .toString();
        }

        return new StringBuilder("Task is in ")
                .append(status.toString())
                .append(" - ")
                .append("Days left: ")
                .append(DateCompare.daysPastDueDate(dueDate) * -1)
                .toString();
    }
}
