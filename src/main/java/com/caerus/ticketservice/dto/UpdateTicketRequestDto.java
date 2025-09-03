package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;

import java.time.Instant;

public record UpdateTicketRequestDto(
        Long id,
        String item,
        String impact,
        String notifyType,
        Long assetsId,
        TicketStatus status,
        TicketPriority priority,
        String assigneeUserId,
        String createdBy,
        Instant startDate,
        Instant dueDate,
        Instant lastUpdated) {
}
