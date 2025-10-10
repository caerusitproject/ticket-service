package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;

import java.time.Instant;
import java.util.List;

public record TicketDto(
        Long id,
        String item,
        String impact,
        String notificationMode,
        String groupName,
        String site,
        String technician,
        String subject,
        Long assetsId,
        String category,
        String subCategory,
        TicketStatus status,
        TicketPriority priority,
        String assigneeUserId,
        String createdBy,
        Instant startDate,
        Instant dueDate,
        Instant lastUpdated,
        boolean deleted,
        TicketDetailRequestDto ticketDetail,
        List<DocumentInfoDto> documents,
        Instant createdAt,
        Instant updatedAt

) {
}
