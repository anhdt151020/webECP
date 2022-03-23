package com.edu.hutech.dao;


import com.edu.hutech.model.UserModel;

public interface IUserDAO extends GenericDAO<UserModel>{
    public UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status);
}
