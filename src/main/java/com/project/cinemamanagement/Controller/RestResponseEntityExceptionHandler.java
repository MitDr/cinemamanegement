package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Exception.DataFoundException;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Exception.DataRequiredException;
import com.project.cinemamanagement.MyResponse.ErrorResponse;
import com.project.cinemamanagement.MyResponse.MyResponse;
import lombok.val;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler{

    @ExceptionHandler(DataFoundException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDataFoundException(DataFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value()),null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value()),null,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DataRequiredException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDataRequiredException(DataRequiredException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ErrorResponse(errors, HttpStatus.BAD_REQUEST.value()));
    }
}