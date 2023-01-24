package com.virtualcave.excercise.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String invalidRequest;
    private String message;

    public ErrorResponse(String invalidRequest, String message) {
        this.invalidRequest = invalidRequest;
        this.message = message;
    }
}
