package com.caerus.ticketservice.enums;

public enum ErrorCode {
    TICKET_NOT_FOUND("Ticket not found with id: "),
    CATEGORY_NOT_FOUND("Category not found with id: ");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage(Long id) {
        return message + id;
    }
}
