package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.dto.TicketDto;
import com.caerus.ticketservice.dto.TicketSearchRequest;
import com.caerus.ticketservice.enums.TicketPriority;
import com.caerus.ticketservice.enums.TicketStatus;
import com.caerus.ticketservice.service.TicketQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketQueryController {

    private final TicketQueryService ticketQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketDto>> getTicketById(@PathVariable Long id) {
        TicketDto ticketDto = ticketQueryService.findTicketById(id);
        return ResponseEntity.ok(ApiResponse.success("Ticket retrieved successfully", ticketDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TicketDto>>> getAllTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant dueDate,
            @RequestParam(required = false) Long ticketId,
            Pageable pageable
    ) {

        TicketSearchRequest request = new TicketSearchRequest(status, priority, startDate, endDate, dueDate, ticketId);
        Page<TicketDto> page = ticketQueryService.getAllTickets(request, pageable);
        return ResponseEntity.ok(ApiResponse.success("Tickets retrieved successfully", PageResponse.from(page)));

    }


}
