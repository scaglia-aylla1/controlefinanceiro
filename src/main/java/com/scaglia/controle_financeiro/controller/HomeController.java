package com.scaglia.controle_financeiro.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status", "/health"})
public class HomeController {

    @GetMapping
    @Operation(summary = "Página de confirmação do perfil")
    public String healthCheck(){
        return "Application is running";
    }
}