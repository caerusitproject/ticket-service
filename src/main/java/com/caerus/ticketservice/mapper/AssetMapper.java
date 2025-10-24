package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.Asset;
import com.caerus.ticketservice.domain.Subcategory;
import com.caerus.ticketservice.dto.AssetDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetDto toDto(Asset asset);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", source = "subcategoryId", qualifiedByName = "mapSubcategory")
    Asset toEntity(AssetDto dto);

    @Named("mapSubcategory")
    default Subcategory mapSubcategory(Long subcategoryId) {
        if (subcategoryId == null) {
            return null;
        }
        Subcategory s = new Subcategory();
        s.setId(subcategoryId);
        return s;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchAssetFromDto(AssetDto dto, @MappingTarget Asset entity);
}
