package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.TicketMapper;
import com.caerus.ticketservice.repository.CategoryRepository;
import com.caerus.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketCommandServiceImpl implements TicketCommandService {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final TicketMapper ticketMapper;

    private Ticket getTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TICKET_NOT_FOUND.getMessage(id)));
    }

    @Override
    @Transactional
    public Long saveTicket(TicketDto ticketDto) {

        Ticket ticket = ticketMapper.toEntity(ticketDto);

        if (ticket.getCategory() != null && ticket.getCategory().getId() == null) {
            Category savedCategory = categoryRepository.save(ticket.getCategory());
            ticket.setCategory(savedCategory);
        }

        if (ticket.getTicketDetails() != null) {
            ticket.getTicketDetails().forEach(detail -> detail.setTicket(ticket));
        }

        if (ticket.getDocuments() != null) {
            ticket.getDocuments().forEach(doc -> doc.setTicket(ticket));
        }

        if (ticket.getStates() != null) {
            ticket.getStates().forEach(state -> state.setTicket(ticket));
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        return savedTicket.getId();
    }

    @Override
    public UpdateTicketRequestDto updateTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto) {
        Ticket ticket = getTicketOrThrow(id);

        ticketMapper.updateTicketFromDto(updateTicketRequestDto, ticket);

        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toUpdateDto(updated);
    }

    @Override
    public UpdateTicketRequestDto patchTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto) {
        Ticket ticket = getTicketOrThrow(id);

        ticketMapper.updateTicketFromDto(updateTicketRequestDto, ticket);

        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toUpdateDto(updated);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Ticket ticket = getTicketOrThrow(id);

        ticket.setDeleted(true);
        ticketRepository.save(ticket);
    }

}
