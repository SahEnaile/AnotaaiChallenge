package com.sarah.anotaai.controllers;

import com.sarah.anotaai.domain.category.CategoryDTO;
import com.sarah.anotaai.domain.category.CategoryResponseDTO;
import com.sarah.anotaai.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryDTO categoryData) {
        return ResponseEntity.ok(service.create(categoryData));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable String id, @RequestBody CategoryDTO categoryData) {
        return ResponseEntity.ok(service.update(id, categoryData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}