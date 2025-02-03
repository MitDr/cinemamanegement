package com.project.cinemamanagement.Exception.Handler;

import com.project.cinemamanagement.Exception.*;
import com.project.cinemamanagement.MyResponse.ErrorResponse;
import jakarta.validation.UnexpectedTypeException;
import org.postgresql.util.PSQLException;

import com.project.cinemamanagement.MyResponse.MyResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.val;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler{

    @ExceptionHandler(DataFoundException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDataFoundException(DataFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.CONFLICT.value()),null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.UNAUTHORIZED.value()),null, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse("Request body is not valid",HttpStatus.BAD_REQUEST.value()),null, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePSQLException(PSQLException ex) {
        String errorMessage;
        // Kiểm tra lỗi vi phạm unique constraint
        if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
            String detailMessage = ex.getServerErrorMessage().getDetail();

            // Kiểm tra và phân tích thông báo chi tiết để tìm tên cột vi phạm
            String columnName = null;
            Pattern pattern = Pattern.compile("\\((.*?)\\)=\\((.*?)\\)");
            Matcher matcher = pattern.matcher(detailMessage);
            if (matcher.find()) {
                columnName = matcher.group(1); // Lấy tên cột
            }

            // Thiết lập thông báo lỗi chi tiết
            if (columnName != null) {
                errorMessage = "duplicate key value violates unique constraint '" + columnName + "'";
            } else {
                errorMessage = "duplicate key value violates unique constraint";
            }

            return new ResponseEntity<>(new ErrorResponse(errorMessage, HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }

        // Trả về lỗi chung nếu không xác định được
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.INTERNAL_SERVER_ERROR);

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
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.UNAUTHORIZED.value()),null,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FailedToUploadException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleFailedToUploadException(FailedToUploadException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({InvalidDataException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()),null,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InternalErrorException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleInternalErrorException(InternalErrorException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()),null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(),HttpStatus.UNAUTHORIZED.value()),null,HttpStatus.UNAUTHORIZED);
    }
}