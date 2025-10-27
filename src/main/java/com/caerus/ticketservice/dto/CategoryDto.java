package com.caerus.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto
        (Long id,
         String categoryCode,

         @NotBlank(message = "Category name is required")
         String categoryName,

         String description,
         Boolean deleted
        ) {
}
