package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.dto.SubcategoryDto;
import com.caerus.ticketservice.service.SubcategoryService;
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
@RequestMapping("/api/v1/categories/{categoryId}/subcategories")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> createSubcategory(@PathVariable Long categoryId, @Valid @RequestBody SubcategoryDto subcategoryDto) {
        SubcategoryDto dtoWithCategory = subcategoryDto.toBuilder()
                .categoryId(categoryId)
                .build();

        Long id = subcategoryService.createSubcategory(dtoWithCategory);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Subcategory created successfully", data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SubcategoryDto>>> getAllSubcategories(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "false") Boolean deleted,
            @RequestParam(required = false) String search,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Subcategories retrieved successfully", subcategoryService.getAllSubcategories(deleted, categoryId, search, pageable)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryDto>> patchSubcategoryById(@PathVariable Long categoryId, @PathVariable Long id, @RequestBody SubcategoryDto subcategoryDto) {
        SubcategoryDto updatedSubcategory = subcategoryService.patchSubcategoryById(categoryId, id, subcategoryDto);
        return ResponseEntity.ok(ApiResponse.success("Subcategory updated successfully", updatedSubcategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryDto>> getSubcategoryById(@PathVariable Long categoryId, @PathVariable Long id) {
        SubcategoryDto subcategoryDto = subcategoryService.getSubcategoryById(categoryId, id);
        return ResponseEntity.ok(ApiResponse.success("Subcategory retrieved successfully", subcategoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubcategory(@PathVariable Long categoryId, @PathVariable Long id) {
        subcategoryService.deleteSubcategoryById(categoryId, id);
        return ResponseEntity.noContent().build();
    }
}
