package com.edu.controller;

import com.edu.entity.Auth;
import com.edu.entity.Menu;
import com.edu.entity.Role;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.service.PermissionService;
import com.edu.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class MenuController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = {"/menu/list", "/menu/list/"})
    public String moveListMenu() {
        return "redirect:/menu/list/1";
    }

    @RequestMapping(value = "/menu/list/{page}")
    public String showMenuList(Model model, @ModelAttribute("searchForm") Menu mMenu, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(30);
        paging.setIndexPage(page);
        List<Menu> menus = accountService.getAllMenu(mMenu, paging);
        List<Role> roles = accountService.getAllRole();
        roles.sort((r1,r2) ->Integer.compare(r1.getId(),r2.getId()));
        menus.forEach(item -> {
            Map<Integer,Integer> mapAuth = new TreeMap<>();
            for (Role role : roles) {
                mapAuth.put(role.getId(), 0);
            }
            for (Auth auth: permissionService.findAuthByProperty("menuId", item.getId()) ) {
                mapAuth.put(auth.getRoleId(),auth.getPermission());
            }
            item.setMapAuth(mapAuth);
        });
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("menus", menus);
        model.addAttribute("roles", roles);
        return "menu-list";
    }

    @GetMapping("/menu/change-status/{id}")
    public String changeActive(Model model, @PathVariable("id") int id, HttpSession session) {
        try {
            Menu menu = accountService.findMenuById(id);
            menu.setActiveFlag(menu.getActiveFlag()== 1 ? 2 : 1);
            accountService.updateMenu(menu);
            session.setAttribute(Constant.MSG_SUCCESS, "Change Status success");
        } catch (Exception e) {
            session.setAttribute(Constant.MSG_ERROR, "Change Status fail");
        }
        return "redirect:/menu/list";
    }

    @GetMapping("/menu/permission")
    public String permission(Model model) {
        model.addAttribute("mAuth", new Auth());
        List<Role> roles = accountService.getAllRole();
        List<Menu> menus = accountService.getAllMenu();
        Map<Integer, String> mapRole = new HashMap<>();
        Map<Integer, String> mapMenu = new HashMap<>();
        for (Role role : roles) {
            mapRole.put(role.getId(), role.getName());
        }
        for (Menu menu : menus) {
            mapMenu.put(menu.getId(), menu.getUrl());
        }
        model.addAttribute("mapRole", mapRole);
        model.addAttribute("mapMenu", mapMenu);
        return "menu-action";
    }

    @PostMapping("/menu/update-permission")
    public String updatePermission(Model model, HttpSession session, @ModelAttribute("mAuth") Auth mAuth) {
        try {
            List<Auth> authList = permissionService.getAllAuth(mAuth, null);
            if (CollectionUtils.isEmpty(authList)) {
                //create
                Auth auth = new Auth();
                auth.setRoleId(mAuth.getRoleId());
                auth.setMenuId(mAuth.getMenuId());
                auth.setPermission(mAuth.getPermission());
                permissionService.createAuth(auth);
            } else {
                //update
                Auth auth = authList.get(0);
                auth.setPermission(mAuth.getPermission());
                permissionService.updateAuth(auth);
            }
            session.setAttribute(Constant.MSG_SUCCESS, "Change Permission success");
        }catch (Exception e) {
            session.setAttribute(Constant.MSG_ERROR, "Change Permission fail");
        }
        return "redirect:/menu/list";
    }
}
