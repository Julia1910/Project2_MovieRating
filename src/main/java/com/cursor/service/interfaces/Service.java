package com.cursor.service.interfaces;

import com.cursor.exceptions.NotFoundException;

import java.util.List;

public interface Service<T> {
    T add(T t);
    T getById(Long id);
    List<T> getAll();
    T remove(Long id);
}
