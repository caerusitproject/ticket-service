package com.caerus.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SubcategoryDto {
    Long id;
    String code;
    String name;
    String description;
    Long categoryId;
}
