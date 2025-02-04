package com.project.cinemamanagement.Exception;

import java.io.Serial;

public class FailedToUploadException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public FailedToUploadException(String message) {
        super(message);
    }
}
