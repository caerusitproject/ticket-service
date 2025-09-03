package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.*;

public interface TicketService {

    Long saveTicket(TicketDto ticketDto);

    TicketDto findTicketById(Long id);

    UpdateTicketRequestDto updateTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    UpdateTicketRequestDto patchTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    void softDeleteById(Long id);

}
