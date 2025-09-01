package com.caerus.ticketservice.domain;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketState {
	
	@Id
    private Integer id;

    private String taskState;

    private Instant taskUpdateOn;
    
    @PreUpdate
    public void onUpdate() {
		this.taskUpdateOn = Instant.now();
	}

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
