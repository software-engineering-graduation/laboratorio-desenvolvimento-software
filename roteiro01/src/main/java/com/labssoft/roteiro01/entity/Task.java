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
    @NonNull
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Builder.Default private TaskStatus status = TaskStatus.InProgress;

    @Override
    public String toString() {
        return "Task [id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", status=" + this.status + "]";
    }
}
