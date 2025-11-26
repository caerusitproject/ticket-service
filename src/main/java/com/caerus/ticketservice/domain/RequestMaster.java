package com.caerus.ticketservice.domain;

import com.caerus.ticketservice.enums.ResolutionType;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "request_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestMaster {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String requester;

  @Column(columnDefinition = "TEXT")
  private String assets;

  private String subject;

  @Column(columnDefinition = "TEXT")
  private String description;

  @ManyToOne
  @JoinColumn(name = "ticket_id")
  private Ticket ticketId;

  @Column(name = "assign_to")
  private Long assignTo;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Enumerated(EnumType.STRING)
  private ResolutionType resolutionType;

  @Column(name = "notify_mode")
  private String notifyMode;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
  protected Instant createdAt;

  @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
  protected Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  public void onUpdate() {
    this.updatedAt = Instant.now();
  }

  @OneToMany(mappedBy = "requestMaster", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ResponseMaster> responses;
}
