package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DocumentInfoMapper {

  @Mapping(target = "id", ignore = true)
  DocumentInfo toEntity(DocumentInfoDto documentDto);

  DocumentInfoDto toDto(DocumentInfo documentInfo);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void patchDocumentInfoFromDto(DocumentInfoRequestDto dto, @MappingTarget DocumentInfo entity);
}
