package com.edu.hutech.service;

import com.edu.hutech.model.UserModel;

public interface IUserService {
    UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status);
}
