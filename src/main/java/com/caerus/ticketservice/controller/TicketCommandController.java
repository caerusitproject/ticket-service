package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.TicketRequestDto;
import com.caerus.ticketservice.dto.UpdateTicketRequestDto;
import com.caerus.ticketservice.service.TicketCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.caerus.ticketservice.constants.TicketMessages.TICKET_CREATED_MSG;
import static com.caerus.ticketservice.constants.TicketMessages.TICKET_UPDATED_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketCommandController {

    private final TicketCommandService ticketCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> createTicket(@Valid @RequestBody TicketRequestDto ticketDto) {
        Long id = ticketCommandService.saveTicket(ticketDto);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(TICKET_CREATED_MSG, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateTicketRequestDto>> updateTicket(@PathVariable Long id, @Valid @RequestBody UpdateTicketRequestDto updateTicketRequestDto) {
        UpdateTicketRequestDto updatedTicket = ticketCommandService.updateTicketById(id, updateTicketRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(TICKET_UPDATED_MSG, updatedTicket));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateTicketRequestDto>> patchTicket(@PathVariable Long id, @RequestBody UpdateTicketRequestDto updateTicketRequestDto) {
        UpdateTicketRequestDto updatedTicket = ticketCommandService.patchTicketById(id, updateTicketRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(TICKET_UPDATED_MSG, updatedTicket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ticketCommandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
