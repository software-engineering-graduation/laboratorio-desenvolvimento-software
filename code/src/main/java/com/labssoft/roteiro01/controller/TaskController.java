package com.labssoft.roteiro01.controller;

import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.dto.ReadTask;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.exceptions.InvalidFieldFormatException;
import com.labssoft.roteiro01.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de tarefas vazia"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Iterable<ReadTask>> listAll() {
        Iterable<ReadTask> tasks = taskService.listAll();
        if (tasks.iterator().hasNext()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "Busca uma tarefa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    public ResponseEntity<ReadTask> findById(@PathVariable("id") Long id) {
        ReadTask task = taskService.findById(id);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/task")
    @Operation(summary = "Cria uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ReadTask> create(
            @RequestBody(description = "Objeto tarefa que será criado.", required = true, content = @Content(schema = @Schema(implementation = CreateTask.class))) @Valid @org.springframework.web.bind.annotation.RequestBody CreateTask task)
            throws InvalidFieldFormatException {
        ReadTask newTask = taskService.create(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Deleta uma tarefa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (taskService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/task/{id}/done")
    @Operation(summary = "Concluir uma tarefa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa concluida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "304", description = "Tarefa já concluida"),
    })
    public ResponseEntity<ReadTask> done(@PathVariable("id") Long id) {
        ReadTask task = taskService.complete(id);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/task/{id}")
    @Operation(summary = "Editar uma tarefa do sistema (Título, Descrição e Status)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa editada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    public ResponseEntity<ReadTask> edit(
            @Valid @RequestBody(description = "Objeto com os novos dados.", required = true, content = @Content(schema = @Schema(implementation = UpdateTask.class))) @org.springframework.web.bind.annotation.RequestBody UpdateTask task,
            @PathVariable("id") Long id) {
        ReadTask updatedTask = taskService.edit(id, task);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}