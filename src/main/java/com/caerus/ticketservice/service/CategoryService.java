package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.CategoryRequestDto;

public interface CategoryService {

    CategoryDto patchCategoryById(Long id, CategoryRequestDto categoryRequestDto);

}
