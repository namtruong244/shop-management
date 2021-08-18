package com.shopmanagement.controller;

import com.shopmanagement.entity.Category;
import com.shopmanagement.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity getAllCategories() {
        return categoryService.findAllDocuments();
    }

    @PostMapping("categories")
    public ResponseEntity saveCategory(@RequestBody Category category){
        return categoryService.saveDocument(category);
    }

    @GetMapping("categories/{categoryId}")
    public ResponseEntity getCategory(@PathVariable String categoryId) {
        return categoryService.getDocumentById(categoryId);
    }

    @PutMapping("categories")
    public ResponseEntity updateProduct(@RequestBody Category category){
        return categoryService.updateDocument(category);
    }

    @DeleteMapping("categories/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable String categoryId){
        return categoryService.deleteDocument(categoryId);
    }
}
