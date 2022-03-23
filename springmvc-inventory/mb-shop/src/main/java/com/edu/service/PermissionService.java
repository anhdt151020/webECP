package com.edu.service;

import com.edu.entity.Auth;
import com.edu.entity.UserRole;
import com.edu.model.Paging;

import java.util.List;

public interface PermissionService {

    //UserRole
    List<UserRole> getAllUserRole();

    List<UserRole> getAllUserRole(UserRole userRole, Paging paging);

    List<UserRole> findUserRoleByProperty(String property, Object value);

    UserRole findUserRoleById(int id);

    void createUserRole(UserRole userRole) throws Exception;

    void updateUserRole(UserRole userRole) throws Exception;

    void deleteUserRole(UserRole userRole) throws Exception;

    //Auth
    List<Auth> getAllAuth();

    List<Auth> getAllAuth(Auth auth, Paging paging);

    List<Auth> findAuthByProperty(String property, Object value);

    Auth findAuthById(int id);

    void createAuth(Auth auth) throws Exception;

    void updateAuth(Auth auth) throws Exception;

    void deleteAuth(Auth auth) throws Exception;
}
