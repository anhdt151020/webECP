package com.edu.dao;

import com.edu.model.Paging;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDAO<E> {
    List<E> findAll();

    List<E> findAll(String sql, Map<String, Object> mapParams, Paging paging);

    E findById(Class<E> e, Serializable id);

    List<E> findByProperty(String prop, Object val);

    void save(E instance);

    void update(E instance);
}