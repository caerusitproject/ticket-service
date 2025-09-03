package com.caerus.ticketservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetailRequestDto {
    private String subject;
    private String comment;
    private String attachment;
}
