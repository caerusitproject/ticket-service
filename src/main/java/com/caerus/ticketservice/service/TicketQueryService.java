package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.TicketSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketQueryService {

  TicketDto findTicketById(Long id);

  Page<TicketDto> getAllTickets(TicketSearchRequest ticketSearchRequest, Pageable pageable);
}
