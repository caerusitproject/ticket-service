package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistory extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ticket_id", nullable = false)
  private Ticket ticket;

  @Column(name = "event_type", length = 50)
  private String eventType;

  @Column(name = "changes_json", columnDefinition = "TEXT")
  private String changesJson;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "performed_by", length = 100)
  private String performedBy;
}
