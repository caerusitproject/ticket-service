package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.*;
import com.caerus.ticketservice.payload.SuccessResponse;
import com.caerus.ticketservice.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final String TICKET_UPDATED_MSG = "Ticket updated successfully";

    @PostMapping
    public ResponseEntity<SuccessResponse<Map<String, Long>>> createTicket(@Valid @RequestBody TicketDto ticketDto) {
        Long id = ticketService.saveTicket(ticketDto);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Ticket created successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<TicketDto>> getTicketById(@PathVariable Long id) {
        TicketDto ticketDto = ticketService.findTicketById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(ticketDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<UpdateTicketRequestDto>> updateTicket(@PathVariable Long id, @Valid @RequestBody UpdateTicketRequestDto updateTicketRequestDto) {
        UpdateTicketRequestDto updatedTicket = ticketService.updateTicketById(id, updateTicketRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(TICKET_UPDATED_MSG, updatedTicket));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<UpdateTicketRequestDto>> patchTicket(@PathVariable Long id, @RequestBody UpdateTicketRequestDto updateTicketRequestDto) {
        UpdateTicketRequestDto updatedTicket = ticketService.patchTicketById(id, updateTicketRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(TICKET_UPDATED_MSG, updatedTicket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteById(@PathVariable Long id) {
        ticketService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<SuccessResponse<CategoryDto>> patchCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryDto updatedCategory = ticketService.patchCategoryById(id, categoryRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Category updated successfully", updatedCategory));
    }

    @PatchMapping("/detail/{id}")
    public ResponseEntity<SuccessResponse<TicketDetailDto>> updateTicketDetails(@PathVariable Long id, @RequestBody TicketDetailRequestDto ticketDetailRequestDto) {
        TicketDetailDto updatedTicketDetails = ticketService.updateTicketDetails(id, ticketDetailRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(TICKET_UPDATED_MSG, updatedTicketDetails));
    }

    @PatchMapping("/document/{id}")
    public ResponseEntity<SuccessResponse<DocumentInfoDto>> patchDocument(@PathVariable Long id, @RequestBody DocumentInfoRequestDto documentRequestDto) {
        DocumentInfoDto updatedDocument = ticketService.patchDocumentById(id, documentRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>("Document updated successfully", updatedDocument));
    }

}
