package com.edu.service.impl;

import com.edu.dao.AuthDAO;
import com.edu.dao.UserRoleDAO;
import com.edu.entity.Auth;
import com.edu.entity.UserRole;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.service.PermissionService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    final static Logger log = Logger.getLogger(PermissionServiceImpl.class);
    @Autowired
    private AuthDAO<Auth> authDAO;
    @Autowired
    private UserRoleDAO<UserRole> userRoleDAO;
    @Autowired
    private AccountService accountService;

    //UserRole
    @Override
    public List<UserRole> getAllUserRole() {
        log.info("[Service::" + "find all UserRole]");
        return userRoleDAO.findAll();
    }

    @Override
    public List<UserRole> getAllUserRole(UserRole userRole, Paging paging) {
        log.info("[Service::" + "find UserRole by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (userRole.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", userRole.getId());
            }
            if (userRole.getRoleId() != null) {
                sql.append(" and model.roleId = :roleId");
                mapParams.put("roleId", userRole.getRoleId());
            }
            if (userRole.getUserId() != null) {
                sql.append(" and model.userId = :userId");
                mapParams.put("userId", userRole.getUserId());
            }
            if (userRole.getUsersByUserId() != null && !StringUtils.isBlank(userRole.getUsersByUserId().getUsername())) {
                sql.append(" and model.usersByUserId.username like :userName");
                mapParams.put("userName", "%" + userRole.getUsersByUserId().getUsername() + "%");
            }
            if (userRole.getRoleByRoleId() != null && !StringUtils.isBlank(userRole.getRoleByRoleId().getName())) {
                sql.append(" and model.roleByRoleId.name like :roleName");
                mapParams.put("roleName", "%" + userRole.getRoleByRoleId().getName() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return userRoleDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<UserRole> findUserRoleByProperty(String property, Object value) {
        log.info("[Service::" + "find UserRole by property]");
        return userRoleDAO.findByProperty(property, value);
    }

    @Override
    public UserRole findUserRoleById(int id) {
        log.info("[Service::" + "find UserRole by id]");
        return userRoleDAO.findById(UserRole.class, id);
    }

    @Override
    public void createUserRole(UserRole userRole) throws Exception {
        log.info("[Service::" + "create UserRole]");
        userRole.setActiveFlag(1);
        userRole.setCreatedDate(new Date());
        userRole.setModifiedDate(new Date());
        userRole.setUsersByUserId(accountService.findUsersById(userRole.getUserId()));
        userRole.setRoleByRoleId(accountService.findRoleById(userRole.getRoleId()));
        userRoleDAO.save(userRole);
    }

    @Override
    public void updateUserRole(UserRole userRole) throws Exception {
        log.info("[Service::" + "update UserRole]");
        userRole.setModifiedDate(new Date());
        userRole.setUsersByUserId(accountService.findUsersById(userRole.getUserId()));
        userRole.setRoleByRoleId(accountService.findRoleById(userRole.getRoleId()));

        UserRole oldUserRole = userRoleDAO.findById(UserRole.class, userRole.getId());
        userRole.setCreatedDate(oldUserRole.getCreatedDate());
        //userRole.setActiveFlag(oldUserRole.getActiveFlag());
        userRoleDAO.update(userRole);
    }

    @Override
    public void deleteUserRole(UserRole userRole) throws Exception {
        log.info("[Service::" + "delete UserRole]");

        UserRole oldUserRole = userRoleDAO.findById(UserRole.class, userRole.getId());
        oldUserRole.setModifiedDate(new Date());
        oldUserRole.setActiveFlag(0);
        userRoleDAO.update(oldUserRole);
    }

    //Auth
    @Override
    public List<Auth> getAllAuth() {
        log.info("[Service::" + "find all Auth]");
        return authDAO.findAll();
    }

    @Override
    public List<Auth> getAllAuth(Auth auth, Paging paging) {
        log.info("[Service::" + "find Auth by params]");
        Map<String, Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (auth.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", auth.getId());
            }
            if (auth.getRoleId() != null) {
                sql.append(" and model.roleId = :roleId");
                mapParams.put("roleId", auth.getRoleId());
            }
            if (auth.getMenuId() != null) {
                sql.append(" and model.menuId = :menuId");
                mapParams.put("menuId", auth.getMenuId());
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return authDAO.findAll(sql.toString(), mapParams, paging);
    }

    @Override
    public List<Auth> findAuthByProperty(String property, Object value) {
        log.info("[Service::" + "find Auth by property]");
        return authDAO.findByProperty(property, value);
    }

    @Override
    public Auth findAuthById(int id) {
        log.info("[Service::" + "find Auth by id]");
        return authDAO.findById(Auth.class, id);
    }

    @Override
    public void createAuth(Auth auth) throws Exception {
        log.info("[Service::" + "create Auth]");
        auth.setActiveFlag(1);
        auth.setCreatedDate(new Date());
        auth.setModifiedDate(new Date());
        auth.setMenuByMenuId(accountService.findMenuById(auth.getMenuId()));
        auth.setRoleByRoleId(accountService.findRoleById(auth.getRoleId()));
        authDAO.save(auth);
    }

    @Override
    public void updateAuth(Auth auth) throws Exception {
        log.info("[Service::" + "update Auth]");
        auth.setModifiedDate(new Date());
        auth.setMenuByMenuId(accountService.findMenuById(auth.getMenuId()));
        auth.setRoleByRoleId(accountService.findRoleById(auth.getRoleId()));

        Auth oldAuth = authDAO.findById(Auth.class, auth.getId());
        auth.setCreatedDate(oldAuth.getCreatedDate());
        auth.setActiveFlag(oldAuth.getActiveFlag());
        authDAO.update(auth);
    }

    @Override
    public void deleteAuth(Auth auth) throws Exception {
        log.info("[Service::" + "delete Auth]");

        Auth oldAuth = authDAO.findById(Auth.class, auth.getId());
        oldAuth.setModifiedDate(new Date());
        oldAuth.setActiveFlag(0);
        authDAO.update(oldAuth);
    }
}
