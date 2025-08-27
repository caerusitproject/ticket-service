package com.caerus.ticketservice.domain;


import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "time_analysis")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeAnalysis {
    @Id
    private Long id;

    private Long requestId;
    private Instant resolution;
    private Instant groupTime;
    private Instant techTime;
}