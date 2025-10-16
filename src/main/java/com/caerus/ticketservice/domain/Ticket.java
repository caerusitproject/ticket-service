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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority = TicketPriority.LOW;

    private String category;
    private String subCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.CREATED;

    private String item;
    private String notificationMode;
    private String impact;
    private String groupName;
    private String site;
    private String technician;
    private String subject;
    private String requester;
    List<String> userEmailIdToNotify;

    @Column(name = "assets_id")
    private Long assetsId;

    private String assigneeUserId;

    private String createdBy;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TicketDetail ticketDetail;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentInfo> documents;


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