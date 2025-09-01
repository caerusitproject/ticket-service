package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String subCategory;
    private String priority;
    private String mode;
    private Long groupId;
    private String technician;
}