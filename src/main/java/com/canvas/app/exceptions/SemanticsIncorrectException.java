package com.canvas.app.exceptions;

public class SemanticsIncorrectException extends Exception {

    public SemanticsIncorrectException(String message) {
        super(message);
    }

    public SemanticsIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
