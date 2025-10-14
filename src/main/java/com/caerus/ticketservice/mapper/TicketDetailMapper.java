package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.TicketDetailDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TicketDetailMapper {

    TicketDetailDto toDto(TicketDetail ticketDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketDetailFromDto(TicketDetailDto dto, @MappingTarget TicketDetail entity);

}
