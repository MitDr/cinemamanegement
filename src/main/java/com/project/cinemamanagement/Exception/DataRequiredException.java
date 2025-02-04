package com.project.cinemamanagement.Exception;

import java.io.Serial;

public class DataRequiredException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public DataRequiredException(String message) {
        super(message);
    }
}
