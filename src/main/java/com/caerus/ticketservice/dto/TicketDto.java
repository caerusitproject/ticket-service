package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;

import java.time.Instant;
import java.util.List;

public record TicketDto(

        Long id,
        String item,
        String impact,
        String notifyType,
        Long assetsId,
        CategoryDto category,
        TicketStatus status,
        TicketPriority priority,
        String assigneeUserId,
        String createdBy,
        Instant startDate,
        Instant dueDate,
        Instant lastUpdated,
        boolean deleted,
        List<TicketDetailDto> ticketDetails,
        List<DocumentInfoDto> documents,
        List<TicketStateDto> states,
        Instant createdAt,
        Instant updatedAt

) {
}
