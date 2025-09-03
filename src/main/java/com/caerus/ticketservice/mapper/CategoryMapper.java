package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.dto.CategoryDto;
import com.caerus.ticketservice.dto.CategoryRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchCategoryFromDto(CategoryRequestDto dto, @MappingTarget Category entity);
}
