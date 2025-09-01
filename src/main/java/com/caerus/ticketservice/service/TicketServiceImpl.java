package com.caerus.ticketservice.service;

import java.util.List;
import java.util.Optional;

import com.caerus.ticketservice.configure.ModelMapperConfig;
import com.caerus.ticketservice.dto.TicketDto;
import org.springframework.stereotype.Service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;
    private final ModelMapperConfig modelMapper;

    @Override
    public Long saveTicket(TicketDto ticketDto) {

        Ticket ticket = modelMapper.getModelMapper().map(ticketDto, Ticket.class);

        Ticket savedTicket = ticketRepository.save(ticket);
        return savedTicket.getId();
    }

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
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

}
