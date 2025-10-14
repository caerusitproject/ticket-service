package com.caerus.ticketservice.exception.file;

import com.caerus.ticketservice.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TempFileAccessException extends ApiException {
    public TempFileAccessException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}