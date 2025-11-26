package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

  Long createCategory(CategoryDto categoryDto);

  CategoryDto patchCategoryById(Long id, CategoryDto categoryRequestDto);

  CategoryDto getCategoryById(Long id);

  PageResponse<CategoryDto> getAllCategories(Boolean deleted, String search, Pageable pageable);

  void deleteCategoryById(Long id);
}
