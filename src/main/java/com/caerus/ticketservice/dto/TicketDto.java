package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import lombok.*;

import java.time.Instant;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    private String item;
    private String impact;
    private String notifyType;
    private Long assetsId;
    private CategoryDto category;
    private TicketStatus status;
    private TicketPriority priority;
    private String assigneeUserId;
    private String createdBy;
    private Instant startDate;
    private Instant dueDate;
    private Instant lastUpdated;
    private boolean deleted;
    private List<TicketDetailDto> ticketDetails;
    private List<DocumentInfoDto> documents;
    private List<TicketStateDto> states;
    private Instant createdAt;
    private Instant updatedAt;

}
