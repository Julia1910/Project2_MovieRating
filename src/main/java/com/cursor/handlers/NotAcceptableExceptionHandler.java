package com.cursor.handlers;

import com.cursor.exceptions.IncorrectMovieDtoException;
import com.cursor.exceptions.IncorrectRateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotAcceptableExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(
            value = {
                    IncorrectMovieDtoException.class,
                    IncorrectRateException.class
            }
    )
    ResponseEntity<Object> handleConflict(
            RuntimeException exception,
            WebRequest request
    ) {
        String[] response = exception.getMessage().split(";");
        return handleExceptionInternal(exception, response[0],
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}
