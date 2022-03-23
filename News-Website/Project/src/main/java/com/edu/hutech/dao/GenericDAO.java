package com.edu.hutech.dao;

import com.edu.hutech.mapper.RowMapper;

import java.util.List;

public interface GenericDAO<T> {
    public List<T> query(String sql, RowMapper rowMapper, Object... param);
    public Long insert(String sql, Object... param);
    public void update(String sql, Object... param);
    public int count(String sql, Object... param);
}
