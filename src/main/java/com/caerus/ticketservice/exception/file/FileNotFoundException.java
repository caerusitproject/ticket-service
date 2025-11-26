package com.caerus.ticketservice.exception.file;

import com.caerus.ticketservice.exception.ApiException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends ApiException {
  public FileNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
