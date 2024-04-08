package com.labssoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CreateTask{
    @Schema(
        description = "Título da tarefa. ", 
        example = "Tarefa 1", 
        maxLength = 100,
        minLength = 1
    )
    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres")
    private String title;

    @Schema(
        description = "Descrição da tarefa. ", 
        example = "Descrição da tarefa 1",
        maxLength = 5000
    )
    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 5000 caracteres")
    private String description;
}