package com.caerus.ticketservice.service;

import java.util.List;
import java.util.Optional;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketDto;

public interface TicketService {

    Long saveTicket(TicketDto ticketDto);
	List<Ticket> findAll();
	Optional<Ticket> findById(Integer id);
	void deleteById(Integer id);
	Ticket updateTicket(Ticket ticket);
	
}
