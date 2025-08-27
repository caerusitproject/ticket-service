package com.caerus.ticketservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements ITicketService {
	@Autowired
	private final TicketRepository ticketRepository;

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public Optional<Ticket> findById(Integer id) {
		return Optional.ofNullable(ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id)));
	}

	

	@Override
	public void deleteById(Integer id) {
		ticketRepository.deleteById(id);

	}


	@Override
	public Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

}
