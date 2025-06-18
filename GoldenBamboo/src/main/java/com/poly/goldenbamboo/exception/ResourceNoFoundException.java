package com.poly.goldenbamboo.exception;

public class ResourceNoFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNoFoundException(String message) {
        super(message);
    }

    public ResourceNoFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
