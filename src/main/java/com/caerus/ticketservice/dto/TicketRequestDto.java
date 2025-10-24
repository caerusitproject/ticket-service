package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public record TicketRequestDto(
        String item,

        @NotBlank
        String impact,

        @NotBlank
        String notificationMode,
        String groupName,

        @NotBlank
        String site,
        String technician,

        @NotBlank
        String subject,
        Long assetsId,
        Long categoryId,
        Long subcategoryId,

        @NotNull
        TicketStatus status,

        @NotNull
        TicketPriority priority,
        String assigneeUserId,

        @NotBlank
        String requester,

        List<String> userEmailIdToNotify,
        String createdBy,
        Instant startDate,
        Instant endDate,
        Instant dueDate,
        Instant lastUpdated,
        boolean deleted,
        TicketDetailRequestDto ticketDetail,
        List<DocumentInfoRequestDto> documents
) {
}
