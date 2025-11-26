package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.TicketHistoryDto;
import com.caerus.ticketservice.service.TicketHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketHistoryController {

  private final TicketHistoryService ticketHistoryService;

  @GetMapping("/{ticketId}/history")
  public ResponseEntity<ApiResponse<List<TicketHistoryDto>>> getTicketHistory(
      @PathVariable Long ticketId) {
    List<TicketHistoryDto> historyList = ticketHistoryService.getHistoryForTicket(ticketId);
    return ResponseEntity.ok(
        ApiResponse.success("Ticket history retrieved successfully", historyList));
  }
}
