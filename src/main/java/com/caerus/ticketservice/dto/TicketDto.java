package com.caerus.ticketservice.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.domain.TicketPriority;
import com.caerus.ticketservice.domain.TicketState;
import com.caerus.ticketservice.domain.TicketStatus;

import jakarta.persistence.PreUpdate;
import lombok.*;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {
	
	   
		private Long id;

		private String item;
		private String impact;
		private String notyftType;
		private Long assetsId;
		private Long categoryId;
		private TicketStatus status = TicketStatus.CREATED;
		private TicketPriority priority = TicketPriority.LOW; // e.g., LOW, MEDIUM, HIGH
		private String assigneeUserId;
		private String createdBy;
		private LocalDateTime startDate;
		private LocalDateTime dueDate;
		private Instant lastUpdated = Instant.now();
		private List<TicketDetail> ticketDetails;
		private List<DocumentInfo> documents;
		private List<TicketState> states;
		private List<UserDto> usersinfo;
		private Instant createdAt = Instant.now();
		private Instant updatedAt = Instant.now();

	    @PreUpdate
	    public void onUpdate() {
	        this.updatedAt = Instant.now();
	        this.lastUpdated = Instant.now();
	    }

}
