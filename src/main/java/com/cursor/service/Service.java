package com.cursor.service;

import java.util.List;

public interface Service<T> {
    T add(T t);
    T getById(int id);
    List<T> getAll();
    T remove(int id);
}
