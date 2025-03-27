package com.microservice.category_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private String message;
    private Map<String, String> details;

    public ErrorResponse( String message, Map<String, String> details) {
        this.timestamp = LocalDate.now().toString();
        this.message = message;
        this.details = details;
    }
}
