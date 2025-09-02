package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetail extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String comment;
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}