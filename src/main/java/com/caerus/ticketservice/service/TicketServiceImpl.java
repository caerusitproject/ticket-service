package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Category;
import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.*;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.TicketMapper;
import com.caerus.ticketservice.repository.CategoryRepository;
import com.caerus.ticketservice.repository.DocumentInfoRepository;
import com.caerus.ticketservice.repository.TicketDetailRepository;
import com.caerus.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final TicketDetailRepository ticketDetailRepository;
    private final DocumentInfoRepository documentInfoRepository;
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
    @Transactional(readOnly = true)
    public TicketDto findTicketById(Long id) {
        Ticket ticket = getTicketOrThrow(id);

        return ticketMapper.toDto(ticket);
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

    @Override
    public CategoryDto patchCategoryById(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.getMessage(id)));

        ticketMapper.patchCategoryFromDto(categoryRequestDto, category);
        return ticketMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public TicketDetailDto updateTicketDetails(Long id, TicketDetailRequestDto ticketDetailRequestDto) {
        TicketDetail ticketDetail = ticketDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket detail not found with id: " + id));

        ticketMapper.patchTicketDetailFromDto(ticketDetailRequestDto, ticketDetail);
        return ticketMapper.toDto(ticketDetailRepository.save(ticketDetail));
    }

    @Override
    public DocumentInfoDto patchDocumentById(Long id, DocumentInfoRequestDto documentRequestDto) {
        DocumentInfo documentInfo = documentInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found with id: " + id));

        ticketMapper.patchDocumentInfoFromDto(documentRequestDto, documentInfo);
        return ticketMapper.toDto(documentInfoRepository.save(documentInfo));
    }

}
