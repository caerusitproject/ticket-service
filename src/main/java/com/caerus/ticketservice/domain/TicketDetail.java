package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskSubject;

    private String taskComment;

    private String taskAttachment;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}