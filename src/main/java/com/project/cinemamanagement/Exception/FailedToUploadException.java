package com.project.cinemamanagement.Exception;

public class FailedToUploadException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public FailedToUploadException(String message) {
        super(message);
    }
}
