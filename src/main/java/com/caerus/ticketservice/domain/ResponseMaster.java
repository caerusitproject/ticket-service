package com.caerus.ticketservice.domain;


import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "response_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private RequestMaster requestMaster;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "doc_link", columnDefinition = "TEXT")
    private String docLink;

    @Column(name = "created_by")
    private String createdBy;

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