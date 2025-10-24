package com.caerus.ticketservice.payload;


import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private Instant timestamp;
    private boolean status = false;
    private int statusCode;
    private String error;
    private String correlationId;

    public ErrorResponse(String error, String correlationId, int statusCode) {
        this.timestamp = Instant.now();
        this.error = error;
        this.correlationId = correlationId;
        this.statusCode = statusCode;
    }
}
