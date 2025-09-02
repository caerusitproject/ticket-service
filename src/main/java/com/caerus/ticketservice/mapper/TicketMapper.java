package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.*;
import com.caerus.ticketservice.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDto toDto(Ticket ticket);

    TicketDetailDto toDto(TicketDetail ticketDetail);

    DocumentInfoDto toDto(DocumentInfo document);

    TicketStateDto toDto(TicketState state);

    CategoryDto toDto(Category category);

    UpdateTicketRequestDto toUpdateDto(Ticket ticket);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Ticket toEntity(TicketDto ticketDto);

    @Mapping(target = "id", ignore = true)
    TicketDetail toEntity(TicketDetailDto ticketDetailDto);

    @Mapping(target = "id", ignore = true)
    DocumentInfo toEntity(DocumentInfoDto documentDto);

    @Mapping(target = "id", ignore = true)
    TicketState toEntity(TicketStateDto stateDto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    void updateTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);
}
