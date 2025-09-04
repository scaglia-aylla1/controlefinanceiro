package com.scaglia.controle_financeiro.controller;

import com.scaglia.controle_financeiro.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Cria um dashboard de rendas e despesas")
    public ResponseEntity<Map<String, Object>> getDashboardData(){
        Map<String, Object> dashboardData = dashboardService.getDashboardData();
        return  ResponseEntity.ok(dashboardData);
    }
}
