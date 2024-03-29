package com.labssoft.roteiro01.entity;

import com.labssoft.roteiro01.enums.TaskStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa. ")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir no máximo 100 caracteres")
    private String title;
    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 100 caracteres")
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status;

    public Task(String title, String description){
        this.title = title;
        this.description = description;
        this.status = TaskStatus.InProgress;
    }

    @Override
    public String toString() {
        return "Task [id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", status=" + this.status + "]";
    }
}
