package com.caerus.ticketservice.dto;

public record CategoryDto
        (Long id,
         String categoryName,
         String subCategory,
         String priority,
         String mode,
         Long groupId,
         String technician) {
}
