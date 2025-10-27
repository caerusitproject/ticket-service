package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.CategoryMapper;
import com.caerus.ticketservice.repository.CategoryRepository;
import com.caerus.ticketservice.repository.spec.CategorySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategorySpecification categorySpecification;

    @Override
    public Long createCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepository.save(categoryMapper.toEntity(categoryDto));
        return savedCategory.getId();
    }

    @Override
    public CategoryDto patchCategoryById(Long id, CategoryDto categoryRequestDto) {
        Category category = getCategoryByIdOrThrowError(id);

        categoryMapper.patchCategoryFromDto(categoryRequestDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    private Category getCategoryByIdOrThrowError(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage(id)));
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = getCategoryByIdOrThrowError(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public PageResponse<CategoryDto> getAllCategories(Boolean deleted, String search, Pageable pageable) {
        pageable = enforceAllowedSort(pageable, List.of("id", "categoryName"));

        boolean isDeleted = Boolean.TRUE.equals(deleted);

        Specification<Category> spec = Specification.allOf(
                categorySpecification.deletedEquals(isDeleted),
                categorySpecification.search(search)
        );

        Page<Category> page = categoryRepository.findAll(spec, pageable);

        Page<CategoryDto> dtoPage = page.map(categoryMapper::toDto);
        return PageResponse.from(dtoPage);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = getCategoryByIdOrThrowError(id);
        category.setDeleted(true);
        categoryRepository.save(category);
        log.info("Category with id {} marked deleted successfully", id);
    }

    private Pageable enforceAllowedSort(Pageable pageable, List<String> allowedSorts) {
        if (pageable.getSort().isUnsorted()) {
            return Pageable.unpaged();
        }

        Sort filtered = Sort.by(
                pageable.getSort().stream()
                        .filter(o -> allowedSorts.contains(o.getProperty()))
                        .toList()
        );
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), filtered);
    }

}
