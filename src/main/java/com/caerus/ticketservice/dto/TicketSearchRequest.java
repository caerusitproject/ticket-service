package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;

import java.time.Instant;

public record TicketSearchRequest(
        TicketStatus status,
        TicketPriority priority,
        Instant startDate,
        Instant endDate,
        Instant dueDate,
        Long ticketId
) {
}
