package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.*;
import com.caerus.ticketservice.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "ticketDetail", target = "ticketDetail")
    TicketDto toDto(Ticket ticket);

    TicketStateDto toDto(TicketState state);

    UpdateTicketRequestDto toUpdateDto(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "ticketDetail", ignore = true)
    Ticket toEntity(TicketDto ticketDto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "deleted", ignore = true)
    void updateTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

}
