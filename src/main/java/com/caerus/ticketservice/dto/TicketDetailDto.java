package com.caerus.ticketservice.dto;

public record TicketDetailDto(
        Long id,
        String subject,
        String comment,
        String attachment
) {
}
