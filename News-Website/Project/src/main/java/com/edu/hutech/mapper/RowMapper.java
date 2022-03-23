package com.edu.hutech.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    public T mapRow(ResultSet resultSet);
}
//T la 1 class
