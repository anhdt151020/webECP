package com.edu.hutech.mapper.impl;

import com.edu.hutech.mapper.RowMapper;
import com.edu.hutech.model.RoleModel;
import com.edu.hutech.model.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserModel> {
    @Override
    public UserModel mapRow(ResultSet resultSet) {
        try {
            UserModel user = new UserModel();
            user.setUserName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFullName(resultSet.getString("fullname"));
            user.setStatus(resultSet.getInt("status"));
            user.setRoleId(resultSet.getLong("roleid"));


            user.setId(resultSet.getLong("id"));
            /*user.setCreatedDate(resultSet.getTimestamp("createddate"));
            user.setCreatedBy(resultSet.getString("createdby"));
            if (resultSet.getTimestamp("modifieddate") != null) {
                user.setModifiedDate(resultSet.getTimestamp("modifieddate"));
            }
            if (resultSet.getString("modifiedby") != null) {
                user.setModifiedBy(resultSet.getString("modifiedby"));
            }*/

            try {
                RoleModel role = new RoleModel();
                role.setName(resultSet.getString("name"));
                role.setCode(resultSet.getString("code"));
                user.setRole(role);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return user;

        } catch (SQLException e) {
            return null;
        }
    }
}
