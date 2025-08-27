package com.caerus.ticketservice.domain;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "document_info")
public class DocumentInfo {
    @Id
    private Integer id;

    private String docType;
    private String docSize;
    private String docUrl;

    @ManyToOne
    @JoinColumn(name = "ticket")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestMaster request;

    private Instant createdOn = Instant.now();
    private Instant updatedOn = Instant.now();
    
    @PreUpdate
    public void onUpdate() {
        this.updatedOn = Instant.now();
    }
}