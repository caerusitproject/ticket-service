package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.*;
import com.caerus.ticketservice.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {DocumentInfoMapper.class, TicketDetailMapper.class})
public interface TicketMapper {

    @Mapping(source = "ticketDetail", target = "ticketDetail")
    @Mapping(source = "documents", target = "documents")
    TicketDto toDto(Ticket ticket);

    TicketStateDto toDto(TicketState state);

    UpdateTicketRequestDto toUpdateDto(Ticket ticket);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "ticketDetail", ignore = true)
    Ticket toEntity(TicketRequestDto ticketDto);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    @Mapping(target = "deleted", ignore = true)
    void updateTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketFromDto(UpdateTicketRequestDto dto, @MappingTarget Ticket entity);

}
