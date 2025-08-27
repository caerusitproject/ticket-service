package com.caerus.ticketservice.domain;


import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;
    
    private String item;
    private String impact;
    private String notyftType;
    

    @Column(name = "assets_id")
    private Long assetsId;

    @Column(name = "category_id")
    private Long categoryId;

   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.CREATED;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority = TicketPriority.LOW; // e.g., LOW, MEDIUM, HIGH
    private String assigneeUserId;
    private String createdBy;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    @Column(name = "last_updated")
    private Instant lastUpdated = Instant.now();
    
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketDetail> ticketDetails;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentInfo> documents;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketState> states;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();
    
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
        this.lastUpdated = Instant.now();
    }
}