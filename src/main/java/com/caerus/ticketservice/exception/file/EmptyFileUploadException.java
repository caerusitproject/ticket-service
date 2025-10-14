package com.caerus.ticketservice.exception.file;

import com.caerus.ticketservice.exception.ApiException;
import org.springframework.http.HttpStatus;

public class EmptyFileUploadException extends ApiException {
    public EmptyFileUploadException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
