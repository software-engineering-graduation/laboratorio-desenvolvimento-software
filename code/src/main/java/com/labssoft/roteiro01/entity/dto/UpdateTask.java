package com.labssoft.roteiro01.entity.dto;

import java.util.Date;

import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.enums.TaskType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdateTask{
    @Schema(
        description = "Título da tarefa. ", 
        example = "Título da tarefa editado",
        maxLength = 100,
        minLength = 1
    )
    @Valid
    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres")
    private String title;
    
    @Schema(
        description = "Descrição da tarefa. ", 
        example = "Descrição da tarefa editada",
        maxLength = 5000
    )
    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 5000 caracteres")
    private String description;

    @Schema(
        description = "Status da tarefa (InProgress/Completed)", 
        example = "Completed"
    )
    private TaskStatus status;

    @Schema(description = "Prioridade da tarefa. ", example = "High")
    private TaskPriority priority;
}