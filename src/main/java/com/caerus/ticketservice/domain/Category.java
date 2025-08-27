package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private Integer id;

    private Long subCategory;
    private String priority;
    private String mode;
    private Long groupId;
    private String technitian;
}