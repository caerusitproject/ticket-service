package com.caerus.ticketservice.mapper;

import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.TicketDetailDto;
import com.caerus.ticketservice.dto.TicketDetailRequestDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketDetailMapper {

    TicketDetailDto toDto(TicketDetail ticketDetail);

    @Mapping(target = "id", ignore = true)
    TicketDetail toEntity(TicketDetailDto ticketDetailDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchTicketDetailFromDto(TicketDetailRequestDto dto, @MappingTarget TicketDetail entity);

}
