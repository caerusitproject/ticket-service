package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.TicketDetailDto;
import com.caerus.ticketservice.dto.TicketDetailRequestDto;
import com.caerus.ticketservice.payload.SuccessResponse;
import com.caerus.ticketservice.service.TicketDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.caerus.ticketservice.constants.TicketMessages.TICKET_UPDATED_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticketDetail")
public class TicketDetailController {

    private final TicketDetailService ticketService;

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<TicketDetailDto>> updateTicketDetails(@PathVariable Long id, @RequestBody TicketDetailRequestDto ticketDetailRequestDto) {
        TicketDetailDto updatedTicketDetails = ticketService.updateTicketDetails(id, ticketDetailRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(TICKET_UPDATED_MSG, updatedTicketDetails));
    }
}
