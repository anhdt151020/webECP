package com.edu.service;

import com.edu.entity.Menu;
import com.edu.entity.Role;
import com.edu.entity.Users;
import com.edu.model.Paging;

import java.util.List;

public interface AccountService {
    //user
    List<Users> getAllUser();

    List<Users> getAllUser(Users users, Paging paging);

    List<Users> findUserByProperty(String property, Object value);

    Users findUsersById(int id);

    void createUsers(Users users) throws Exception;

    void updateUsers(Users users) throws Exception;

    void deleteUsers(Users users) throws Exception;

    //role
    List<Role> getAllRole();

    List<Role> getAllRole(Role role, Paging paging);

    List<Role> findRoleByProperty(String property, Object value);

    Role findRoleById(int id);

    void createRole(Role role) throws Exception;

    void updateRole(Role role) throws Exception;

    void deleteRole(Role role) throws Exception;

    //menu
    List<Menu> getAllMenu();

    List<Menu> getAllMenu(Menu menu, Paging paging);

    List<Menu> findMenuByProperty(String property, Object value);

    Menu findMenuById(int id);

    void createMenu(Menu menu) throws Exception;

    void updateMenu(Menu menu) throws Exception;

    void deleteMenu(Role menu) throws Exception;
}
