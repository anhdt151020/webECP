package com.edu.hutech.service.impl;

import com.edu.hutech.dao.IUserDAO;
import com.edu.hutech.model.UserModel;
import com.edu.hutech.service.IUserService;

import javax.inject.Inject;

public class UserService implements IUserService {
    @Inject
    private IUserDAO userDAO;
    @Override
    public UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status) {
        return userDAO.findByUserNameAndPasswordAndStatus(userName, password, status);
    }
}
