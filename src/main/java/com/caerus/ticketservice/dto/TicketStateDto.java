package com.caerus.ticketservice.dto;

import com.caerus.ticketservice.enums.TicketStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketStateDto {
    private Long id;
    private TicketStatus ticketStatus;
}