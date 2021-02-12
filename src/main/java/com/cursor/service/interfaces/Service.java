package com.cursor.service.interfaces;

import java.util.List;

public interface Service<T> {
    T add(T t);
    T getById(Long id);
    List<T> getAll();
    void remove(Long id);
}
