package com.caerus.ticketservice.dto;

public record TicketDetailRequestDto(
        String subject,
        String comment,
        String attachment
) {
}
