package com.caerus.ticketservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {
    private String categoryName;
    private String subCategory;
    private String priority;
    private String mode;
    private Long groupId;
    private String technician;
}
