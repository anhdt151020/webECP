package com.edu.security;

import com.edu.entity.Menu;
import com.edu.entity.Role;
import com.edu.entity.Users;
import com.edu.service.AccountService;
import com.edu.service.PermissionService;
import com.edu.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterSercurity implements HandlerInterceptor {
    final static Logger log = Logger.getLogger(FilterSercurity.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[Security::" + request.getRequestURI() + "]");
        Users u = (Users) request.getSession().getAttribute(Constant.USER_INFO);
        if (u == null) {
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        } else {
            String url = request.getServletPath();
            if (StringUtils.isNumeric(url.substring(url.lastIndexOf('/') + 1))) { // /list/1, /list/2 -> /list
                url = url.substring(0, url.lastIndexOf('/'));
            }
            if (checkPermission(url, u)) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/access-denied");
                return false;
            }
        }
    }
    private boolean checkPermission(String url,Users user) {
        //
        // get listRole from 1User - allow
        List<Role> listRole = new ArrayList<>(); //chứa nRole(1User)(nUserRole)
        permissionService.findUserRoleByProperty("userId", user.getId()) //nUserRole(1User) - active
                .forEach(o -> {
                    if (o.getActiveFlag() == 1) { // 1-RoleAllowed , 2-RoleBlocked
                        listRole.add(o.getRoleByRoleId());
                    }
                });
        //
        // get listMenu from nAuth - allow
        Map<Integer, Menu> mapMenu = new HashMap<>(); //chứa nMenu thuộc nRole - allow (ko trùng lặp)
        listRole.forEach(itemRole -> { //1Role - nAuth
            permissionService.findAuthByProperty("roleId", itemRole.getId()).forEach(itemAuth -> { //listAuth
                if (itemAuth.getPermission() == 1 && itemAuth.getMenuByMenuId().getActiveFlag() == 1) { //nRole sẽ có MenuAllowed trùng
                    mapMenu.put(itemAuth.getMenuId(), itemAuth.getMenuByMenuId());
                }
            });
        });
        //
        // compare url(user-session) <> url(request in browser)
        for (Map.Entry<Integer,Menu> entry: mapMenu.entrySet()) {
            if (url.equalsIgnoreCase(entry.getValue().getUrl())) {
                return true;
            }
        }
        return false;
    }

}
