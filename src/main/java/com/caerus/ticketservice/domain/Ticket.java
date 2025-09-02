package com.caerus.ticketservice.domain;


import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    private String item;
    private String impact;
    private String notifyType;


    @Column(name = "assets_id")
    private Long assetsId;

    @ManyToOne
    @JoinColumn(name = "category_id")
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

    @PrePersist
    protected void onCreateTicket() {
        super.onCreate();
        this.lastUpdated = this.updatedAt;
    }

    @PreUpdate
    public void onUpdateTicket() {
        this.lastUpdated = this.updatedAt;
    }
}