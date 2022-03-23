package com.edu.hutech.mapper.impl;

import com.edu.hutech.mapper.RowMapper;
import com.edu.hutech.model.NewModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewMapper implements RowMapper<NewModel> {
    @Override
    public NewModel mapRow(ResultSet resultSet) {

        try {
            NewModel news = new NewModel();
            news.setTitle(resultSet.getString("title"));
            news.setContent(resultSet.getString("content"));
            news.setCategoryId(resultSet.getLong("categoryid"));
            news.setThumbnail(resultSet.getString("thumbnail"));
            news.setShortDescription(resultSet.getString("shortdescription"));
            
            news.setId(resultSet.getLong("id"));
            news.setCreatedDate(resultSet.getTimestamp("createddate"));
            news.setCreatedBy(resultSet.getString("createdby"));
            if (resultSet.getTimestamp("modifieddate") != null) {
                news.setModifiedDate(resultSet.getTimestamp("modifieddate"));
            }
            if (resultSet.getString("modifiedby") != null) {
                news.setModifiedBy(resultSet.getString("modifiedby"));
            }

            return news;
        } catch (SQLException e) {
            return null;
        }
    }
}
