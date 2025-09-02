package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTicketRequestDto {
    private Long id;
    private String item;
    private String impact;
    private String notifyType;
    private Long assetsId;
    private TicketStatus status;
    private TicketPriority priority;
    private String assigneeUserId;
    private String createdBy;
    private Instant startDate;
    private Instant dueDate;
    private Instant lastUpdated;
}
