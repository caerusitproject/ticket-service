package com.caerus.ticketservice.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "task_detail")
public class TicketDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskSubject;
    private String taskComment;
    private String taskAttachment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Ticket ticket;
}