package com.caerus.ticketservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfoDto {
    private Long id;
    private String docType;
    private String docSize;
    private String docUrl;
}
