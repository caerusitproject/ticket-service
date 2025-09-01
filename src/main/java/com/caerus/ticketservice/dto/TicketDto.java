package com.caerus.ticketservice.dto;

import java.time.Instant;
import java.util.List;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.domain.TicketDetail;
import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.domain.TicketState;
import com.caerus.ticketservice.enums.TicketStatus;

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
		private String notifyType;
		private Long assetsId;
		private Long categoryId;
		private TicketStatus status;
		private TicketPriority priority;
		private String assigneeUserId;
		private String createdBy;
		private Instant startDate;
		private Instant dueDate;
		private Instant lastUpdated;
		private List<TicketDetail> ticketDetails;
		private List<DocumentInfo> documents;
		private List<TicketState> states;
		private List<UserDto> userinfo;
		private Instant createdAt;
		private Instant updatedAt;

}
