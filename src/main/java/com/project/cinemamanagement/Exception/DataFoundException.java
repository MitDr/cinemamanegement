package com.project.cinemamanagement.Exception;

import java.io.Serial;

public class DataFoundException extends  RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public DataFoundException(String message) {
        super(message);
    }
}
