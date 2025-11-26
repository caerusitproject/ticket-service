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
public class TicketDetail extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "ticket_id", nullable = false)
  private Ticket ticket;

  @Column(columnDefinition = "text")
  private String content;
}
