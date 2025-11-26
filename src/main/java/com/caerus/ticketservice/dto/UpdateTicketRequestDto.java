package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import java.time.Instant;

public record UpdateTicketRequestDto(
    String item,
    String impact,
    String notificationMode,
    String groupName,
    String site,
    String technician,
    String subject,
    Long assetsId,
    Long categoryId,
    Long subcategoryId,
    TicketStatus status,
    TicketPriority priority,
    String assigneeUserId,
    String createdBy,
    String updatedBy,
    Instant startDate,
    Instant dueDate,
    Instant lastUpdated,
    boolean deleted,
    Instant createdAt,
    Instant updatedAt,
    TicketDetailDto ticketDetail) {}
