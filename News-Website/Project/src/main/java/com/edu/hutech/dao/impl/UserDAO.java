package com.edu.hutech.dao.impl;

import com.edu.hutech.dao.IUserDAO;
import com.edu.hutech.mapper.impl.UserMapper;
import com.edu.hutech.model.UserModel;

import java.util.List;

public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {

    @Override
    public UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status) {
        StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
        sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
        sql.append(" WHERE username = ? AND password = ? AND status = ?");

        List<UserModel> users = query(sql.toString(), new UserMapper(), userName, password, status);

        return users.isEmpty() ? null : users.get(0);
    }
}
