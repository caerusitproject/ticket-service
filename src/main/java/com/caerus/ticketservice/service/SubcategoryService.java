package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.dto.SubcategoryDto;
import org.springframework.data.domain.Pageable;

public interface SubcategoryService {
    Long createSubcategory(SubcategoryDto subcategoryDto);

    PageResponse<SubcategoryDto> getAllSubcategories(Long categoryId, String search, Pageable pageable);

    SubcategoryDto patchSubcategoryById(Long categoryId, Long id, SubcategoryDto subcategoryDto);

    SubcategoryDto getSubcategoryById(Long categoryId, Long id);
}
