package com.cursor.handlers;

import com.cursor.exceptions.IncorrectIdException;
import com.cursor.exceptions.IncorrectMovieDtoException;
import com.cursor.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(
            value = {
                    IncorrectMovieDtoException.class,
                    NotFoundException.class,
                    IncorrectIdException.class
            }
    )
    ResponseEntity<Object> handleConflict(
            RuntimeException exception,
            WebRequest request
    ) {
        String[] bodyOfResponse = exception.getMessage().split(";");
        return handleExceptionInternal(exception, bodyOfResponse[0],
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
