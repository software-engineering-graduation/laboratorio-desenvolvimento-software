package com.labssoft.roteiro01.entity.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.labssoft.roteiro01.enums.TaskPriority;
import com.labssoft.roteiro01.enums.TaskType;

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
public class CreateTask {
    @Schema(description = "Título da tarefa. ", example = "Tarefa 1", maxLength = 100, minLength = 1)
    @Size(max = 100, min = 1, message = "Título da tarefa deve possuir pelo menos 1 caractere e no máximo 100 caracteres")
    private String title;

    @Schema(description = "Descrição da tarefa. ", example = "Descrição da tarefa 1", maxLength = 5000)
    @Size(max = 5000, message = "Descrição da tarefa deve possuir no máximo 5000 caracteres")
    private String description;

    @Schema(description = "Tipo da tarefa. ", example = "Date")
    private TaskType type;

    @Schema(description = "Caso a tarefa seja do tipo Date, informar a data de vencimento maior que a data atual.", example = "05/04/2024", pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dueDate;

    @Schema(description = "Caso a tarefa seja do tipo Period, informar a quantidade de dias para vencimento.", example = "5")
    private Integer dueDays;

    @Schema(description = "Prioridade da tarefa. ", example = "High")
    private TaskPriority priority;
}