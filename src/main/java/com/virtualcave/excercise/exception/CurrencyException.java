package com.virtualcave.excercise.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyException extends RuntimeException {

    final Throwable exception;

    public CurrencyException(String message, Throwable exception) {
        super(message);
        this.exception = exception;
    }
}
