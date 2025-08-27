package com.caerus.ticketservice.service;

import java.util.List;
import java.util.Optional;

import com.caerus.ticketservice.domain.Ticket;

public interface ITicketService {
	
	List<Ticket> findAll();
	Optional<Ticket> findById(Integer id);
	Ticket saveTicket(Ticket ticket);
	void deleteById(Integer id);
	Ticket updateTicket(Ticket ticket);
	
}
