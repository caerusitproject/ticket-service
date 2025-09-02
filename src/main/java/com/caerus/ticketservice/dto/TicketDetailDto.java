package com.caerus.ticketservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetailDto {
    private Long id;
    private String subject;
    private String comment;
    private String attachment;
}
