package com.nttdata.bc.services;

import java.util.List;

public interface ICRUD<T, V> {
    T insert(T obj);

    T update(T obj);

    List<T> findAll();

    T findById(V id);

    void delete(V id);
}
