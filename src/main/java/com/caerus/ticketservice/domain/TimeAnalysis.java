package com.caerus.ticketservice.domain;


import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "time_analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId;
    private Instant resolution;
    private Instant groupTime;
    private Instant techTime;
}