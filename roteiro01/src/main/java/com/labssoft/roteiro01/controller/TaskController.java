package com.labssoft.roteiro01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TaskController {
    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public String listAll() {
        return "Listar todas as tasks";
    }
}