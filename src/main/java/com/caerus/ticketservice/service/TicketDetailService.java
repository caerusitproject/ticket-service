package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.TicketDetailDto;
import com.caerus.ticketservice.dto.TicketDetailRequestDto;

public interface TicketDetailService {

    TicketDetailDto updateTicketDetails(Long id, TicketDetailRequestDto ticketDetailRequestDto);

}
