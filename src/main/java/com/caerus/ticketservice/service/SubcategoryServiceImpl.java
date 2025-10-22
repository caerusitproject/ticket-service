package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Subcategory;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.dto.SubcategoryDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.SubcategoryMapper;
import com.caerus.ticketservice.repository.SubcategoryRepository;
import com.caerus.ticketservice.repository.spec.SubcategorySpecification;
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
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryMapper subcategoryMapper;
    private final SubcategorySpecification subcategorySpecification;

    @Override
    public Long createSubcategory(SubcategoryDto subcategoryDto) {
        Subcategory entity = subcategoryMapper.toEntity(subcategoryDto);
        Subcategory savedSubcategory = subcategoryRepository.save(entity);
        return savedSubcategory.getId();
    }

    @Override
    public PageResponse<SubcategoryDto> getAllSubcategories(Long categoryId, String search, Pageable pageable) {
        pageable = enforceAllowedSort(pageable, List.of("id", "name"));

        Specification<Subcategory> specFinal = subcategorySpecification.byCategory(categoryId)
                .and(subcategorySpecification.search(search));
        Page<Subcategory> page = subcategoryRepository.findAll(specFinal, pageable);

        Page<SubcategoryDto> dtoPage = page.map(subcategoryMapper::toDto);
        return PageResponse.from(dtoPage);
    }

    @Override
    public SubcategoryDto patchSubcategoryById(Long categoryId, Long id, SubcategoryDto subcategoryDto) {
        Subcategory category = getSubcategoryByIdOrThrowError(id);

        subcategoryMapper.patchSubcategoryFromDto(subcategoryDto, category);
        return subcategoryMapper.toDto(subcategoryRepository.save(category));
    }

    @Override
    public SubcategoryDto getSubcategoryById(Long categoryId, Long id) {
        Subcategory category = getSubcategoryByIdOrThrowError(id);
        return subcategoryMapper.toDto(category);
    }

    private Subcategory getSubcategoryByIdOrThrowError(Long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SUBCATEGORY_NOT_FOUND.getMessage(id)));
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
