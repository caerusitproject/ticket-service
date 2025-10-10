package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.TicketDetailDto;
import com.caerus.ticketservice.dto.TicketDetailRequestDto;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.TicketDetailMapper;
import com.caerus.ticketservice.repository.TicketDetailRepository;
import com.caerus.ticketservice.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketDetailServiceImpl implements TicketDetailService {

    private final TicketDetailRepository ticketDetailRepository;
    private final TicketDetailMapper ticketDetailMapper;
    private final TicketRepository ticketRepository;

    @Override
    public TicketDetailDto updateTicketDetails(Long id, TicketDetailRequestDto ticketDetailRequestDto) {
        TicketDetail ticketDetail = ticketDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket detail not found with id: " + id));

        ticketDetailMapper.patchTicketDetailFromDto(ticketDetailRequestDto, ticketDetail);
        return ticketDetailMapper.toDto(ticketDetailRepository.save(ticketDetail));
    }

    @Override
    public void saveTicketDetail(Long ticketId, String contentHtml) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        TicketDetail ticketDetail = TicketDetail.builder()
                .ticket(ticket)
                .content(contentHtml)
                .build();

        ticketDetailRepository.save(ticketDetail);
    }

}
