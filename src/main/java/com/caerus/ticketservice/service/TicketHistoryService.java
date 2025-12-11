package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.domain.TicketHistory;
import com.caerus.ticketservice.dto.TicketHistoryDto;
import com.caerus.ticketservice.enums.TicketStatus;
import com.caerus.ticketservice.exception.BadRequestException;
import com.caerus.ticketservice.repository.TicketHistoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketHistoryService {

    private final TicketHistoryRepository historyRepository;
    private final ObjectMapper objectMapper;

    public void recordUpdateEvent(
            Ticket ticket, TicketSnapshot oldSnap, TicketSnapshot newSnap, String performedBy) {
        Map<String, Map<String, Object>> changes = new LinkedHashMap<>();

        compare(changes, "status", oldSnap.status(), newSnap.status());
        compare(changes, "priority", oldSnap.priority(), newSnap.priority());
        compare(changes, "item", oldSnap.item(), newSnap.item());
        compare(changes, "notificationMode", oldSnap.notificationMode(), newSnap.notificationMode());
        compare(changes, "impact", oldSnap.impact(), newSnap.impact());
        compare(changes, "groupName", oldSnap.groupName(), newSnap.groupName());
        compare(changes, "site", oldSnap.site(), newSnap.site());
        compare(changes, "technician", oldSnap.technician(), newSnap.technician());
        compare(changes, "subject", oldSnap.subject(), newSnap.subject());
        compare(changes, "requester", oldSnap.requester(), newSnap.requester());
        compare(changes, "assigneeUserId", oldSnap.assigneeUserId(), newSnap.assigneeUserId());
        compare(changes, "categoryId", oldSnap.categoryId(), newSnap.categoryId());
        compare(changes, "subcategoryId", oldSnap.subcategoryId(), newSnap.subcategoryId());
        compare(changes, "assetId", oldSnap.assetId(), newSnap.assetId());
        compare(changes, "startDate", oldSnap.startDate(), newSnap.startDate());
        compare(changes, "endDate", oldSnap.endDate(), newSnap.endDate());
        compare(changes, "dueDate", oldSnap.dueDate(), newSnap.dueDate());

        if (changes.isEmpty()) return;

        try {
            TicketHistory history =
                    TicketHistory.builder()
                            .ticket(ticket)
                            .eventType("UPDATED")
                            .changesJson(objectMapper.writeValueAsString(changes))
                            .performedBy(performedBy)
                            .description("Ticket updated")
                            .build();

            historyRepository.save(history);
        } catch (Exception e) {
            throw new BadRequestException("Failed to serialize changes JSON " + e);
        }
    }

    private void compare(
            Map<String, Map<String, Object>> changes, String field, Object oldVal, Object newVal) {
        if (!Objects.equals(oldVal, newVal)) {
            changes.put(field, Map.of("old", oldVal, "new", newVal));
        }
    }

    private TicketHistoryDto toDto(TicketHistory h) {
        try {
            Map<String, Object> changes =
                    objectMapper.readValue(h.getChangesJson(), new TypeReference<>() {
                    });

            return new TicketHistoryDto(
                    h.getId(),
                    h.getEventType(),
                    changes,
                    h.getPerformedBy(),
                    h.getDescription(),
                    h.getCreatedAt());
        } catch (Exception e) {
            throw new BadRequestException("Failed to parse changes JSON " + e);
        }
    }

    public List<TicketHistoryDto> getHistoryForTicket(Long ticketId) {
        return historyRepository.findByTicketIdOrderByCreatedAtDesc(ticketId).stream()
                .map(this::toDto)
                .toList();
    }

    public void recordCreateEvent(Ticket ticket, String performedBy) {
        log.info("Recording history for ticketId={}, performedBy={}",
                ticket.getId(), performedBy);
        TicketHistory history =
                TicketHistory.builder()
                        .ticket(ticket)
                        .eventType(TicketStatus.CREATED.name())
                        .performedBy(performedBy)
                        .description("Ticket created")
                        .build();

        historyRepository.saveAndFlush(history);
    }
}
