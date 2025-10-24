package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Long id = categoryService.createCategory(categoryDto);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully", data));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> patchCategory(@PathVariable Long id, @RequestBody CategoryDto categoryRequestDto) {
        CategoryDto updatedCategory = categoryService.patchCategoryById(id, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Category updated successfully", updatedCategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Category retrieved successfully", categoryDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryDto>>> getAllCategories(
            @RequestParam(required = false) String search,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Subcategories retrieved successfully", categoryService.getAllCategories(search, pageable)));
    }
}
