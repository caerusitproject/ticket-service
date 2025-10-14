package com.caerus.ticketservice.exception.file;

import com.caerus.ticketservice.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FileSecurityException extends ApiException {
    public FileSecurityException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}