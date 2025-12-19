package com.soa.ejbvehicle.exception;

import java.io.Serializable;

public class ValidationException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public ValidationException(String message) {
        super(message);
    }
}

