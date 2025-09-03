package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.CategoryRequestDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.CategoryMapper;
import com.caerus.ticketservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto patchCategoryById(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage(id)));

        categoryMapper.patchCategoryFromDto(categoryRequestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
}
