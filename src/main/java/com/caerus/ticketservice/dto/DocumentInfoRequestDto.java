package com.caerus.ticketservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfoRequestDto {
    private String docType;
    private String docSize;
    private String docUrl;
}