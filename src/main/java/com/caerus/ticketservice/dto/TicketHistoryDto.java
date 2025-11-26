package com.caerus.ticketservice.dto;

import java.time.Instant;
import java.util.Map;

public record TicketHistoryDto(
    Long id,
    String eventType,
    Map<String, Object> changes,
    String performedBy,
    String description,
    Instant createdAt) {}
