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
public class DocumentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String docType;
    private String docSize;
    private String docUrl;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestMaster request;

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
}