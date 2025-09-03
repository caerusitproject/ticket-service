package com.caerus.ticketservice.dto;

public record CategoryRequestDto
        (String categoryName,
         String subCategory,
         String priority,
         String mode,
         Long groupId,
         String technician) {
}
