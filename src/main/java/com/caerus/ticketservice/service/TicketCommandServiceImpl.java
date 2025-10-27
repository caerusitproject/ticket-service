package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import com.caerus.ticketservice.dto.TicketDetailRequestDto;
import com.caerus.ticketservice.dto.TicketRequestDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.exception.file.FileMoveException;
import com.caerus.ticketservice.file.FileStorageService;
import com.caerus.ticketservice.mapper.TicketDetailMapper;
import com.caerus.ticketservice.mapper.TicketMapper;
import com.caerus.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final DocumentInfoService documentInfoService;

    private Ticket getTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TICKET_NOT_FOUND.getMessage(id)));
    }

    @Override
    @Transactional
    public Long saveTicket(TicketRequestDto ticketDto) {

        Ticket ticket = buildTicketEntity(ticketDto);
        Ticket savedTicket = ticketRepository.saveAndFlush(ticket);

        List<String> fileUrls = collectAllFileUrls(ticketDto);
        if (fileUrls.isEmpty()) {
            return savedTicket.getId();
        }

        Map<String, String> movedFilesMap = moveFilesAndGetUpdatedPaths(savedTicket.getId(), fileUrls);
        updateTicketFileReferences(ticket, movedFilesMap);
        if (ticketDto.documents() != null && !ticketDto.documents().isEmpty()) {
            List<DocumentInfo> newDocs = ticketDto.documents().stream()
                    .map(d -> DocumentInfo.builder()
                            .docType(d.docType())
                            .docSize(d.docSize())
                            .docUrl(d.docUrl())
                            .ticket(ticket)
                            .build())
                    .toList();

            documentInfoService.replaceDocumentsForTicket(ticket, newDocs);
        }

        ticketRepository.save(ticket);
        return savedTicket.getId();
    }

    private Ticket buildTicketEntity(TicketRequestDto dto) {
        Ticket ticket = ticketMapper.toEntity(dto);

        if (dto.ticketDetail() != null && dto.ticketDetail().content() != null) {
            TicketDetail detail = TicketDetail.builder()
                    .content(dto.ticketDetail().content())
                    .ticket(ticket)
                    .build();
            ticket.setTicketDetail(detail);
        }

        if (ticket.getDocuments() != null) {
            ticket.getDocuments().forEach(doc -> doc.setTicket(ticket));
        }

        return ticket;
    }

    private List<String> collectAllFileUrls(TicketRequestDto dto) {
        List<String> documentUrls = Optional.ofNullable(dto.documents())
                .orElse(List.of())
                .stream()
                .map(DocumentInfoRequestDto::docUrl)
                .filter(Objects::nonNull)
                .toList();

        List<String> editorUrls = Optional.ofNullable(dto.ticketDetail())
                .map(TicketDetailRequestDto::content)
                .filter(content -> content != null && !content.isBlank())
                .map(this::extractImageUrls)
                .orElse(List.of());

        return Stream.concat(documentUrls.stream(), editorUrls.stream())
                .filter(url -> url != null && !url.isBlank())
                .distinct()
                .toList();
    }

    private Map<String, String> moveFilesAndGetUpdatedPaths(Long ticketId, List<String> fileUrls) {
        try {
            return fileStorageService.moveTempFilesToTicketFolder(ticketId, fileUrls);
        } catch (Exception e) {
            log.error("Failed to move temp files for ticket {}: {}", ticketId, e.getMessage(), e);
            throw new FileMoveException("File move failed for ticket " + ticketId, e);
        }
    }

    private void updateTicketFileReferences(Ticket ticket, Map<String, String> movedFilesMap) {
        if (ticket.getDocuments() != null) {
            ticket.getDocuments().forEach(doc -> {
                String newUrl = movedFilesMap.get(doc.getDocUrl());
                if (newUrl != null) doc.setDocUrl(newUrl);
            });
        }

        if (ticket.getTicketDetail() != null) {
            String updatedContent = replaceImageUrls(ticket.getTicketDetail().getContent(), movedFilesMap);
            ticket.getTicketDetail().setContent(updatedContent);
        }
    }

    private String replaceImageUrls(String html, Map<String, String> movedFilesMap) {
        if (html == null || html.isBlank() || movedFilesMap.isEmpty()) return html;

        String updated = html;
        for (Map.Entry<String, String> entry : movedFilesMap.entrySet()) {
            updated = updated.replace(entry.getKey(), entry.getValue());
        }
        return updated;
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
    public void deleteById(Long id) {
        Ticket ticket = getTicketOrThrow(id);

        ticketRepository.deleteById(id);
    }

}
