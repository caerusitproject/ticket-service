package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketStatus;

public record TicketStateDto(
        Long id,
        TicketStatus ticketStatus) {
}