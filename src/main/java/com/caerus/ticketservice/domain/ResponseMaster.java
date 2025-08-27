package com.caerus.ticketservice.domain;


import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "response_master")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMaster {

    @Id
    private Long id;

    @Column(name = "request_id")
    private Long requestId;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "doc_link", columnDefinition = "TEXT")
    private String docLink;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();
    
    @PreUpdate
    public void onUpdate() {
		this.updatedOn = Instant.now();
	}
}