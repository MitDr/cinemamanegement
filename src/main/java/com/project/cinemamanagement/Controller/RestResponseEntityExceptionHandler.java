package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.MyResponse.MyResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler{

    @ExceptionHandler(DataFoundException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public MyResponse handleDataFoundException(DataFoundException ex, WebRequest request) {
        return new MyResponse(null, ex.getMessage(), HttpStatus.CONFLICT.value());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new MyResponse(null, errors.toString(), HttpStatus.BAD_REQUEST.value()));
    }
}