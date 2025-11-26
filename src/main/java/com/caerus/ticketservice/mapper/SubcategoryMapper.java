package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.domain.Subcategory;
import com.caerus.ticketservice.dto.SubcategoryDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {
  @Mapping(target = "categoryId", source = "category.id")
  SubcategoryDto toDto(Subcategory subcategory);

  @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
  @Mapping(target = "deleted", defaultValue = "false")
  Subcategory toEntity(SubcategoryDto dto);

  @Named("mapCategory")
  default Category mapCategory(Long categoryId) {
    if (categoryId == null) {
      return null;
    }
    Category c = new Category();
    c.setId(categoryId);
    return c;
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void patchSubcategoryFromDto(SubcategoryDto dto, @MappingTarget Subcategory entity);
}
