package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.TicketSearchRequest;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.TicketMapper;
import com.caerus.ticketservice.repository.TicketRepository;
import com.caerus.ticketservice.repository.spec.TicketSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketQueryServiceImpl implements TicketQueryService {

  private final TicketMapper ticketMapper;
  private final TicketRepository ticketRepository;

  private Ticket getTicketOrThrow(Long id) {
    return ticketRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorCode.TICKET_NOT_FOUND.getMessage(id)));
  }

  @Override
  @Transactional(readOnly = true)
  public TicketDto findTicketById(Long id) {
    Ticket ticket = getTicketOrThrow(id);

    return ticketMapper.toDto(ticket);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TicketDto> getAllTickets(TicketSearchRequest ticketSearchRequest, Pageable pageable) {
    Specification<Ticket> spec = TicketSpecification.withFilters(ticketSearchRequest);
    Page<Ticket> tickets = ticketRepository.findAll(spec, pageable);

    return tickets.map(ticketMapper::toDto);
  }
}
