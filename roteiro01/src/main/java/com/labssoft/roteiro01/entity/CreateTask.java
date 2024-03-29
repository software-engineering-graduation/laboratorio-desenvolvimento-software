package com.labssoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTask{
    @Schema(description = "Título da tarefa. ", example = "Tarefa 1")
    private String title;
    @Schema(description = "Descrição da tarefa. ", example = "Descrição da tarefa 1")
    private String description;

    public CreateTask(String title) {
        this.title = title;
    }
}