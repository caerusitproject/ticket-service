package com.caerus.ticketservice.domain;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfo extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docType;
    private String docSize;
    private String docUrl;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}