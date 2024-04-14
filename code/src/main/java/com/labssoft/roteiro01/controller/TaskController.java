package com.labssoft.roteiro01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labssoft.roteiro01.entity.dto.CreateTask;
import com.labssoft.roteiro01.entity.Task;
import com.labssoft.roteiro01.entity.dto.UpdateTask;
import com.labssoft.roteiro01.enums.TaskStatus;
import com.labssoft.roteiro01.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @SuppressWarnings("null")
    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Lista de tarefas vazia"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<Task>> listAll() {
        try {
            List<Task> taskList = new ArrayList<Task>();
            taskRepository.findAll()
                    .forEach(taskList::add);

            if (taskList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(taskList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("null")
    @PostMapping("/task")
    @Operation(summary = "Cria uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Task> create(
            @RequestBody(
                description = "Objeto tarefa que será criado.", 
                required = true, 
                content = @Content(
                    schema = @Schema(implementation = CreateTask.class)
                    )
                ) 
              @Valid  @org.springframework.web.bind.annotation.RequestBody CreateTask task) {
        try {
            System.out.println("Creating task: " + task);
            Task _task = taskRepository.save(new Task(task.getTitle(), task.getDescription()));
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("null")
    @DeleteMapping("/task/{id}")
    @Operation(summary = "Deleta uma tarefa do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    public ResponseEntity<Null> delete(@PathVariable("id") Long id){
        try{
            Optional<Task> task = taskRepository.findById(id);

            if(task.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            taskRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("null")
    @PatchMapping("/task/{id}/done")
    @Operation(summary = "Concluir uma tarefa do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa concluida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
        @ApiResponse(responseCode = "304", description = "Tarefa já concluida"),
    })
    public ResponseEntity<Task> done(@PathVariable("id") Long id){
        try{
            Optional<Task> task = taskRepository.findById(id);

            if(!task.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            if(task.get().getStatus() == TaskStatus.Completed){
                return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
            }

            Task updateTask = task.get();
            updateTask.setStatus(TaskStatus.Completed);
            taskRepository.save(updateTask);
            return new ResponseEntity<>(updateTask, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("null")
    @PutMapping("/task/{id}")
    @Operation(summary = "Editar uma tarefa do sistema (Título, Descrição e Status)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa editada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
    })
    public ResponseEntity<Task> edit(@Valid @RequestBody(
        description = "Objeto com os novos dados.", 
        required = true, 
        content = @Content(
            schema = @Schema(implementation = UpdateTask.class)
            )
        ) 
        @org.springframework.web.bind.annotation.RequestBody UpdateTask task,
        @PathVariable("id") Long id
        ){
        try{
            Optional<Task> _task = taskRepository.findById(id);

            if(!_task.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            Task updateTask = _task.get();
            updateTask.setStatus(task.getStatus());
            updateTask.setDescription(task.getDescription());
            updateTask.setTitle(task.getTitle());
            taskRepository.save(updateTask);
            return new ResponseEntity<>(updateTask, HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}