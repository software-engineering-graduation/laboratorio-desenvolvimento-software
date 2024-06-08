package com.labssoft.roteiro01.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/environment")
public class EnvironmentController {
    private final Environment environment;

    @GetMapping
    public String getEnvironmentName() {
        return "{\"environmentName\": \"" + environment.getProperty("environment.name") + "\"}";
    }
}