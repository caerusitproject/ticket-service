package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.*;
import com.caerus.ticketservice.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {
                DocumentInfoMapper.class,
                TicketDetailMapper.class,
                CategoryMapper.class,
                SubcategoryMapper.class,
                AssetMapper.class
        })
public interface TicketMapper {

    @Mapping(source = "ticketDetail", target = "ticketDetail")
    @Mapping(source = "documents", target = "documents")
    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "subcategory", target = "subcategory")
    TicketDto toDto(Ticket ticket);

    TicketStateDto toDto(TicketState state);

    UpdateTicketRequestDto toUpdateDto(Ticket ticket);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "ticketDetail", ignore = true)
    @Mapping(target = "documents", source = "documents")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryFromId")
    @Mapping(target = "subcategory", source = "subcategoryId", qualifiedByName = "mapSubcategoryFromId")
    @Mapping(target = "asset", source = "assetsId", qualifiedByName = "mapAssetFromId")
    Ticket toEntity(TicketRequestDto ticketDto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "deleted", ignore = true)
    void updateTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

    @Named("mapCategoryFromId")
    default Category mapCategoryFromId(Long id) {
        if (id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Named("mapSubcategoryFromId")
    default Subcategory mapSubcategoryFromId(Long id) {
        if (id == null) return null;
        Subcategory subcategory = new Subcategory();
        subcategory.setId(id);
        return subcategory;
    }

    @Named("mapAssetFromId")
    default Asset mapAssetFromId(Long id) {
        if (id == null) return null;
        Asset asset = new Asset();
        asset.setId(id);
        return asset;
    }

}
