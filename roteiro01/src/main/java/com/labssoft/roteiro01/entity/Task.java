package com.labssoft.roteiro01.entity;

import java.util.UUID;

import com.labssoft.roteiro01.enums.TaskStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa. ")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir no máximo 100 caracteres")
    private String title;
    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 100 caracteres")
    private String description;
    private TaskStatus status;

    public Task(String title, String description){
        this.title = title;
        this.description = description;
        this.status = TaskStatus.InProgress;
    }

    @Override
    public String toString() {
        return "Task [uuid=" + uuid + ", title=" + title + ", description=" + description + ", status=" + status + "]";
    }
}
