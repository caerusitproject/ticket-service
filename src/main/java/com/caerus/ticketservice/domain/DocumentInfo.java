package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentInfo extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "doc_type")
  private String docType;

  @Column(name = "doc_size")
  private String docSize;

  @Column(name = "doc_url")
  private String docUrl;

  @ManyToOne
  @JoinColumn(name = "ticket_id")
  private Ticket ticket;
}
