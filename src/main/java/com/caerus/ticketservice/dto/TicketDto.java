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
    AssetDto asset,
    CategoryDto category,
    SubcategoryDto subcategory,
    TicketStatus status,
    TicketPriority priority,
    String requester,
    List<String> userEmailIdToNotify,
    String assigneeUserId,
    String createdBy,
    Instant startDate,
    Instant endDate,
    Instant dueDate,
    Instant lastUpdated,
    boolean deleted,
    TicketDetailDto ticketDetail,
    List<DocumentInfoDto> documents,
    Instant createdAt,
    Instant updatedAt) {}
