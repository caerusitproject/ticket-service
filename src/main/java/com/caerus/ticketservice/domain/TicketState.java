package com.caerus.ticketservice.domain;

import java.time.Instant;

import com.caerus.ticketservice.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketState extends AuditableEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
