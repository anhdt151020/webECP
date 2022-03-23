package com.edu.service.impl;

import com.edu.dao.MenuDAO;
import com.edu.dao.RoleDAO;
import com.edu.dao.UsersDAO;
import com.edu.entity.Menu;
import com.edu.entity.Role;
import com.edu.entity.Users;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.util.HashingPassword;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    final static Logger log = Logger.getLogger(AccountServiceImpl.class);
    @Autowired
    private UsersDAO<Users> usersDAO;
    @Autowired
    private RoleDAO<Role> roleDAO;
    @Autowired
    private MenuDAO<Menu> menuDAO;

    //Users
    @Override
    public List<Users> getAllUser() {
        log.info("[Service::" + "find all User]");
        return usersDAO.findAll();
    }

    @Override
    public List<Users> getAllUser(Users users, Paging paging) {
        log.info("[Service::" + "find User by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (!StringUtils.isBlank(users.getUsername())) {
                sql.append(" and model.username like :username");
                mapParams.put("username", "%" + users.getUsername() + "%");
            }
            if (!StringUtils.isBlank(users.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + users.getName() + "%");
            }
            if (!StringUtils.isBlank(users.getEmail())) {
                sql.append(" and model.email like :email");
                mapParams.put("email", "%" + users.getEmail() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return usersDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<Users> findUserByProperty(String property, Object value) {
        log.info("[Service::" + "find User by property]");
        return usersDAO.findByProperty(property, value);
    }
    @Override
    public Users findUsersById(int id) {
        log.info("[Service::" + "find Users by id]");
        return usersDAO.findById(Users.class, id);
    }
    @Override
    public void createUsers(Users users) throws Exception {
        log.info("[Service::" + "create User]");
        users.setActiveFlag(1);
        users.setCreatedDate(new Date());
        users.setModifiedDate(new Date());
        users.setPassword(HashingPassword.encrypt(users.getPassword()));
        usersDAO.save(users);
    }

    @Override
    public void updateUsers(Users users) throws Exception {
        log.info("[Service::" + "update Users]");
        users.setModifiedDate(new Date());

        Users oldUsers = usersDAO.findById(Users.class, users.getId());
        users.setCreatedDate(oldUsers.getCreatedDate());
        users.setActiveFlag(oldUsers.getActiveFlag());
        if (StringUtils.isBlank(users.getPassword())) { //edit from admin (not from currentuser)
            users.setPassword(oldUsers.getPassword());
        }
        usersDAO.update(users);
    }

    @Override
    public void deleteUsers(Users users) throws Exception {
        log.info("[Service::" + "delete Users]");

        Users oldUsers = usersDAO.findById(Users.class, users.getId());
        oldUsers.setModifiedDate(new Date());
        oldUsers.setActiveFlag(0);
        usersDAO.update(oldUsers);
    }

    //Role
    @Override
    public List<Role> getAllRole() {
        log.info("[Service::" + "find all Role]");
        return roleDAO.findAll();
    }

    @Override
    public List<Role> getAllRole(Role role, Paging paging) {
        log.info("[Service::" + "find Role by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (!StringUtils.isBlank(role.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + role.getName() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return roleDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<Role> findRoleByProperty(String property, Object value) {
        log.info("[Service::" + "find Role by property]");
        return roleDAO.findByProperty(property, value);
    }

    @Override
    public Role findRoleById(int id) {
        log.info("[Service::" + "find Role by id]");
        return roleDAO.findById(Role.class, id);
    }

    @Override
    public void createRole(Role role) throws Exception {
        log.info("[Service::" + "create Role]");
        role.setActiveFlag(1);
        role.setCreatedDate(new Date());
        role.setModifiedDate(new Date());
        roleDAO.save(role);
    }

    @Override
    public void updateRole(Role role) throws Exception {
        log.info("[Service::" + "update Role]");
        role.setModifiedDate(new Date());

        Role oldRole = roleDAO.findById(Role.class, role.getId());
        role.setCreatedDate(oldRole.getCreatedDate());
        role.setActiveFlag(oldRole.getActiveFlag());
        roleDAO.update(role);
    }

    @Override
    public void deleteRole(Role role) throws Exception {
        log.info("[Service::" + "delete Role]");

        Role oldRole = roleDAO.findById(Role.class, role.getId());
        oldRole.setModifiedDate(new Date());
        oldRole.setActiveFlag(0);
        roleDAO.update(oldRole);
    }

    //Menu
    @Override
    public List<Menu> getAllMenu() {
        log.info("[Service::" + "find all Menu]");
        return menuDAO.findAll();
    }

    @Override
    public List<Menu> getAllMenu(Menu menu, Paging paging) {
        log.info("[Service::" + "find Menu by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (!StringUtils.isBlank(menu.getUrl())) {
                sql.append(" and model.url like :url");
                mapParams.put("url", "%" + menu.getUrl() + "%");
            }
            if (!StringUtils.isBlank(menu.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + menu.getName() + "%");
            }

        } catch (Exception e) {
            mapParams = null;
        }
        return menuDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<Menu> findMenuByProperty(String property, Object value) {
        log.info("[Service::" + "find Menu by property]");
        return menuDAO.findByProperty(property, value);
    }

    @Override
    public Menu findMenuById(int id) {
        log.info("[Service::" + "find Menu by id]");
        return menuDAO.findById(Menu.class, id);
    }

    @Override
    public void createMenu(Menu menu) throws Exception {
        log.info("[Service::" + "create Menu]");
        menu.setActiveFlag(1);
        menu.setCreatedDate(new Date());
        menu.setModifiedDate(new Date());
        menuDAO.save(menu);
    }

    @Override
    public void updateMenu(Menu menu) throws Exception {
        log.info("[Service::" + "update Menu]");
        menu.setModifiedDate(new Date());

        Menu oldMenu = menuDAO.findById(Menu.class, menu.getId());
        menu.setCreatedDate(oldMenu.getCreatedDate());
        //menu.setActiveFlag(oldMenu.getActiveFlag());
        menuDAO.update(menu);
    }

    @Override
    public void deleteMenu(Role menu) throws Exception {
        log.info("[Service::" + "delete Menu]");

        Menu oldMenu = menuDAO.findById(Menu.class, menu.getId());
        oldMenu.setModifiedDate(new Date());
        oldMenu.setActiveFlag(0);
        menuDAO.update(oldMenu);
    }
}
