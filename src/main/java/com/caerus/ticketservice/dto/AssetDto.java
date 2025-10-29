package com.caerus.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AssetDto(
        Long id,
        String assetTag,
        String assetName,
        String description,
        Instant purchaseDate,
        Instant warrantyExpireDate,
        String location,
        String status,
        Double cost,
        String vendorName,
        @NotBlank(message = "Serial number is required")
        String serialNumber,

        @NotNull(message = "Subcategory ID is required")
        Long subcategoryId,

        Boolean deleted
) {
}
