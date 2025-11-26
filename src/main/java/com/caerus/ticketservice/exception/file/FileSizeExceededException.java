package com.caerus.ticketservice.exception.file;

import com.caerus.ticketservice.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FileSizeExceededException extends ApiException {
  public FileSizeExceededException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
