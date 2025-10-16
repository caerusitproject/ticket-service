package com.caerus.ticketservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assetTag;
    private String assetName;
    private String description;
    private Long categoryId;
    private Long subCategoryId;
    private Instant purchaseDate;
    private Instant warrantyExpireDate;
    private String location;
    private String status;
    private Double cost;
    private String vendorName;
    private String serialNumber;
}
