package com.scaglia.controle_financeiro.controller;

import com.scaglia.controle_financeiro.dto.IncomeDTO;
import com.scaglia.controle_financeiro.repository.CategoryRepository;
import com.scaglia.controle_financeiro.repository.ProfileRepository;
import com.scaglia.controle_financeiro.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    @PostMapping
    @Operation(summary = "Cria uma renda(receita0")
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO dto){
        IncomeDTO saved = incomeService.addIncome(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    @Operation(summary = "Busca todas as receitas")
    public ResponseEntity<List<IncomeDTO>> getIncomes(){
        List<IncomeDTO> expenses = incomeService.getCurrentMonthIncomesForCurrentUser();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma receita")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id){
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }

}