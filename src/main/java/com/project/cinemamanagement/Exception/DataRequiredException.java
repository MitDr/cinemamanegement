package com.project.cinemamanagement.Exception;

public class DataRequiredException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public DataRequiredException(String message) {
        super(message);
    }
}
