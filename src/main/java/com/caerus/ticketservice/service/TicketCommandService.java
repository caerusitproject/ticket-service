package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.*;

public interface TicketCommandService {

    Long saveTicket(TicketRequestDto ticketDto);

    UpdateTicketRequestDto updateTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    UpdateTicketRequestDto patchTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto);

    void deleteById(Long id);

}
