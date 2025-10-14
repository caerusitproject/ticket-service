package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import com.caerus.ticketservice.dto.TicketRequestDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.file.FileStorageService;
import com.caerus.ticketservice.mapper.TicketDetailMapper;
import com.caerus.ticketservice.mapper.TicketMapper;
import com.caerus.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketCommandServiceImpl implements TicketCommandService {

    private final TicketRepository ticketRepository;
    private final FileStorageService fileStorageService;
    private final TicketMapper ticketMapper;
    private final TicketDetailMapper ticketDetailMapper;

    private Ticket getTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TICKET_NOT_FOUND.getMessage(id)));
    }

    @Override
    @Transactional
    public Long saveTicket(TicketRequestDto ticketDto) {

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

        List<String> documentUrls = ticketDto.documents() == null ? List.of()
                : ticketDto.documents().stream()
                .map(DocumentInfoRequestDto::docUrl)
                .filter(Objects::nonNull)
                .toList();

        List<String> editorUrls = extractImageUrls(ticketDto.ticketDetail().content());

        List<String> allFileUrls = Stream.concat(documentUrls.stream(), editorUrls.stream()).toList();

        fileStorageService.moveTempFilesToTicketFolder(savedTicket.getId(), allFileUrls);

        return savedTicket.getId();
    }

    private List<String> extractImageUrls(String html) {
        if (html == null || html.isBlank()) return List.of();

        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]?([^'\">\\s]+)['\"]?[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        List<String> urls = new ArrayList<>();

        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }

    @Override
    public UpdateTicketRequestDto updateTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto) {
        Ticket ticket = getTicketOrThrow(id);

        ticketMapper.updateTicketFromDto(updateTicketRequestDto, ticket);

        if (updateTicketRequestDto.ticketDetail() != null) {
            if (ticket.getTicketDetail() == null) {
                ticket.setTicketDetail(new TicketDetail());
                ticket.getTicketDetail().setTicket(ticket);
            }
            ticketDetailMapper.patchTicketDetailFromDto(updateTicketRequestDto.ticketDetail(), ticket.getTicketDetail());
            ;
        }
        Ticket updated = ticketRepository.save(ticket);
        return ticketMapper.toUpdateDto(updated);
    }

    @Override
    public UpdateTicketRequestDto patchTicketById(Long id, UpdateTicketRequestDto updateTicketRequestDto) {
        Ticket ticket = getTicketOrThrow(id);

        ticketMapper.patchTicketFromDto(updateTicketRequestDto, ticket);

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
