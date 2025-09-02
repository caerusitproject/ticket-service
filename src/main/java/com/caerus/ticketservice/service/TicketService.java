package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;

import java.util.List;

public interface TicketService {

    Long saveTicket(TicketDto ticketDto);

    TicketDto findTicketById(Long id);

    UpdateTicketRequestDto updateTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    UpdateTicketRequestDto patchTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    List<Ticket> findAll();

    void deleteById(Integer id);

}
