package com.scaglia.controle_financeiro.controller;

import com.scaglia.controle_financeiro.dto.CategoryDTO;
import com.scaglia.controle_financeiro.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Cria uma categoria")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    @Operation(summary = "Busca todas as categorias")
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> categories = categoryService.getCategoriesForCurrentUser();
        return  ResponseEntity.ok(categories);
    }
    @GetMapping("/{type}")
    @Operation(summary = "Busca as categorias por tipo")
    public ResponseEntity<List<CategoryDTO>> getCategoryByTypeForCurrentUser(@PathVariable String type){
        List<CategoryDTO> list = categoryService.getCategoryByTypeForCurrentUser(type);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Atualiza uma categoria")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody CategoryDTO categoryDTO){
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId,categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

}