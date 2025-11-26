package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "asset_tag")
  private String assetTag;

  @Column(name = "asset_name")
  private String assetName;

  private String description;

  @Column(name = "purchase_date")
  private Instant purchaseDate;

  @Column(name = "warranty_expire_date")
  private Instant warrantyExpireDate;

  private String location;
  private String status;
  private Double cost;

  @Column(name = "vendor_name")
  private String vendorName;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "is_deleted")
  private Boolean deleted = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subcategory_id", nullable = false)
  private Subcategory subcategory;
}
