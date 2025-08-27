package com.caerus.ticketservice.exception;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException{
    private HttpStatus status;
    private String message;
}