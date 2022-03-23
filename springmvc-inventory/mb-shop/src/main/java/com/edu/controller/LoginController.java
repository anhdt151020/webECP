package com.edu.controller;

import com.edu.entity.*;
import com.edu.model.MenuModel;
import com.edu.service.*;
import com.edu.util.Constant;
import com.edu.validate.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private LoginValidator loginValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == Users.class) {
            binder.setValidator(loginValidator);
        }
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("mLoginForm", new Users());
        return "login/login";
    }

    @PostMapping("/processLogin")
    public String postlogin(Model model, @ModelAttribute("mLoginForm") @Validated Users mLoginForm, BindingResult result, HttpSession session){
        if (result.hasErrors()){
            return "login/login";
        }
        Users u = accountService.findUserByProperty("username", mLoginForm.getUsername()).get(0);
        session.setAttribute(Constant.USER_INFO, u);
        session.setAttribute(Constant.MENU_SESSION, initMenuModel(u));
        return "redirect:/index";
    }
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Constant.MENU_SESSION);
        session.removeAttribute(Constant.USER_INFO);
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    private ArrayList<MenuModel> initMenuModel(Users user) {
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
                    if (itemAuth.getPermission() == 1) { //nRole sẽ có MenuAllowed trùng
                        mapMenu.put(itemAuth.getMenuId(),itemAuth.getMenuByMenuId());
                    }
                });
        });
        //
        // get menu cha - con
        ArrayList<MenuModel> parentMenu = new ArrayList<>();
        ArrayList<MenuModel> childMenu = new ArrayList<>();
        mapMenu.forEach((k,v) -> {
            MenuModel menuModel = new MenuModel();
            menuModel.setRecentMenu(v);
            menuModel.setIdMenu(v.getUrl().replace("/", "") + "Id"); // /category/edit >> categoryeditId
            if (v.getParentId() == 0 && v.getOrderIndex() != -1 && v.getActiveFlag() == 1) { //là menu cha & ko bị ẩn & active
                parentMenu.add(menuModel);
            } else if (v.getParentId() != 0 && v.getOrderIndex() != -1 && v.getActiveFlag() == 1) { //là menu con & ko bị ẩn & active
                childMenu.add(menuModel);
            } else {
                //do nothing
            }
        });
        //
        // sap xep thanh tree theo orderindex
        for (MenuModel item : parentMenu) {
            for (MenuModel item2 : childMenu) {
                if (item.getRecentMenu().getId() == item2.getRecentMenu().getParentId()) {
                    item.getChild().add(item2);
                }
            }
            item.getChild().sort((m1,m2) -> Integer.compare(m1.getRecentMenu().getOrderIndex(), m2.getRecentMenu().getOrderIndex()));
        }
        parentMenu.sort((m1, m2) -> Integer.compare(m1.getRecentMenu().getOrderIndex(), m2.getRecentMenu().getOrderIndex()));
        //
        return parentMenu;
    }

}
