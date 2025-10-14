package com.caerus.ticketservice.payload;


import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private boolean status = false;
    private int statusCode;
    private String error;
    private String message;
    private String correlationId;

    public ErrorResponse(String error, String message, String correlationId, int statusCode) {
        this.timestamp = Instant.now();
        this.error = error;
        this.message = message;
        this.correlationId = correlationId;
        this.statusCode = statusCode;
    }
}
