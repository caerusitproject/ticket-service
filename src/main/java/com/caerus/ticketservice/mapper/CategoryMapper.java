package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.dto.CategoryDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", defaultValue = "false")
    Category toEntity(CategoryDto categoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchCategoryFromDto(CategoryDto dto, @MappingTarget Category entity);
}
