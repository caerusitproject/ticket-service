package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.CategoryRequestDto;
import com.caerus.ticketservice.payload.SuccessResponse;
import com.caerus.ticketservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoryDto>> patchCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto updatedCategory = categoryService.patchCategoryById(id, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Category updated successfully", updatedCategory));
    }
}
