package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.file.FileStorageService;
import com.caerus.ticketservice.mapper.TicketMapper;
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
    private final FileStorageService fileStorageService;
    private final TicketMapper ticketMapper;

    private Ticket getTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TICKET_NOT_FOUND.getMessage(id)));
    }

    @Override
    @Transactional
    public Long saveTicket(TicketDto ticketDto) {

        Ticket ticket = ticketMapper.toEntity(ticketDto);

        if (ticketDto.ticketDetail() != null && ticketDto.ticketDetail().content() != null) {
            TicketDetail detail = TicketDetail.builder()
                    .content(ticketDto.ticketDetail().content())
                    .ticket(ticket)
                    .build();
            ticket.setTicketDetail(detail);
        }


        if (ticket.getDocuments() != null) {
            ticket.getDocuments().forEach(doc -> doc.setTicket(ticket));
        }

        Ticket savedTicket = ticketRepository.saveAndFlush(ticket);
        fileStorageService.moveTempFilesToTicketFolder(savedTicket.getId());

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
