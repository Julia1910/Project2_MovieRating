package com.cursor.service.interfaces;

import com.cursor.exceptions.NotFoundException;

import java.util.List;

public interface Service<T> {
    T add(T t);
    T getById(Long id) throws NotFoundException;
    List<T> getAll();
    void remove(Long id);
}
