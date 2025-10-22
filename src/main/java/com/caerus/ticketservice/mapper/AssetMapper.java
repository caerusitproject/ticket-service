package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.Asset;
import com.caerus.ticketservice.dto.AssetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetDto toDto(Asset asset);

    @Mapping(target = "id", ignore = true)
    Asset toEntity(AssetDto dto);
}
