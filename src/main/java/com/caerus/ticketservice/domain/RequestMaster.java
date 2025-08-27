package com.caerus.ticketservice.domain;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_master")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMaster {
    @Id
    private Long id;

    private String requestor;
    @Column(columnDefinition = "TEXT")
    private String assets;  
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "ticket_id")
    private Long ticketId;
    @Column(name = "assign_to")
    private Long assignTo;
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "created_on", nullable = false, updatable = false)
    private Instant createdOn = Instant.now();
    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();
    
    @PreUpdate
    public void onUpdate() {
        this.updatedOn = Instant.now();
    }

    @Column(name = "notify_mode")
    private String notifyMode;
}