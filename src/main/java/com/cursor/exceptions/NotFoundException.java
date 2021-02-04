package com.cursor.exceptions;

public class NotFoundException extends Throwable {

    public NotFoundException(Long id) {
        super(String.format("Record with ID = %s not found ", id));
    }
}
