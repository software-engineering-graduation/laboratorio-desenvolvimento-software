package com.labssoft.roteiro01.entity;

import java.util.Date;
import java.lang.StringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

@Entity
@Getter
@Setter
@Schema(description = "Todos os detalhes sobre uma tarefa. ")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres")
    @NonNull
    private String title;

    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 5000 caracteres")
    @Builder.Default private String description = "";

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default private TaskStatus status = TaskStatus.InProgress;

    @Enumerated(EnumType.ORDINAL)
    private TaskType type;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dueDate;

    @Nullable
    private Integer dueDays;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.InProgress;
        this.type = TaskType.Free;
        this.priority = TaskPriority.Low;
    }

    public Task(String title, String description, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.status = TaskStatus.InProgress;
        this.priority = priority;
        this.type = TaskType.Free;
    }

    public Task(String title, String description, TaskType type, Date dueDate, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = TaskStatus.InProgress;
    }

    public Task(String title, String description, TaskType type, Integer dueDays, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.dueDays = dueDays;
        this.priority = priority;
        this.status = TaskStatus.InProgress;
    }

    public Task(String title, String description, TaskStatus status, TaskType type, Date dueDate, Integer dueDays, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
        this.dueDate = dueDate;
        this.dueDays = dueDays;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return new StringBuilder()
        .append("Task [id=").append(id)
        .append(", title=").append(title)
        .append(", description=").append(description)
        .append(", status=").append(status)
        .append(", type=").append(type)
        .append(", dueDate=").append(dueDate)
        .append(", dueDays=").append(dueDays)
        .append(", priority=").append(priority)
        .append("]")
        .toString();
    }
}
