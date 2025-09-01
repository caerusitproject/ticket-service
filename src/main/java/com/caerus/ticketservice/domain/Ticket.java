package com.caerus.ticketservice.domain;


import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;
    
    private String item;
    private String impact;
    private String notifyType;
    

    @Column(name = "assets_id")
    private Long assetsId;

    @OneToOne
    @JoinColumn(name = "category_id") //validate
    private Category category;

   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority = TicketPriority.LOW;

    private String assigneeUserId;

    private String createdBy;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketDetail> ticketDetails;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentInfo> documents;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketState> states;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
    protected Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.lastUpdated = now;
    }
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
        this.lastUpdated = Instant.now();
    }
}