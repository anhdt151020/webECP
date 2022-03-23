package com.edu.controller;

import com.edu.entity.*;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.service.PermissionService;
import com.edu.util.Constant;
import com.edu.validate.UserRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserRoleController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRoleValidator userRoleValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == UserRole.class) {
            binder.setValidator(userRoleValidator);
        }
    }

    @RequestMapping(value = {"/userrole/list", "/userrole/list/"})
    public String moveListUserRole() {
        return "redirect:/userrole/list/1";
    }

    @RequestMapping(value = "/userrole/list/{page}")
    public String showUserRoleList(Model model, @ModelAttribute("searchForm") UserRole mUserRole, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<UserRole> userRoles = permissionService.getAllUserRole(mUserRole, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("userRoles", userRoles);
        return "userrole-list";
    }

    @GetMapping("/userrole/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add User-Role");
        model.addAttribute("mUserRole", new UserRole());
        model.addAttribute("mapUsers", initMapUsers());
        model.addAttribute("mapRoles", initMapRole());
        return "userrole-action";
    }

    @PostMapping("/userrole/save")
    public String save(Model model, @ModelAttribute("mUserRole") @Validated UserRole mUserRole, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("titlePage", "Add User-Role");
            model.addAttribute("mUserRole", mUserRole);
            model.addAttribute("mapUsers", initMapUsers());
            model.addAttribute("mapRoles", initMapRole());
            return "userrole-action";
        }
        try {
            permissionService.createUserRole(mUserRole);
            session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
        } catch (Exception e) {
            session.setAttribute(Constant.MSG_ERROR, "Insert fail");
        }
        return "redirect:/userrole/list";
    }

    @GetMapping("/userrole/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        UserRole userRole = permissionService.findUserRoleById(id);
        if (userRole != null) {
            try {
                permissionService.deleteUserRole(userRole);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/userrole/list";
    }

    @GetMapping("/userrole/change-status/{id}")
    public String changeActive(Model model, @PathVariable("id") int id, HttpSession session) {
        try {
            UserRole userRole = permissionService.findUserRoleById(id);
            userRole.setActiveFlag(userRole.getActiveFlag() == 1 ? 2 : 1);
            permissionService.updateUserRole(userRole);
            session.setAttribute(Constant.MSG_SUCCESS, "Change Status success");
        } catch (Exception e) {
            session.setAttribute(Constant.MSG_ERROR, "Change Status fail");
        }
        return "redirect:/userrole/list";
    }

    private Map<String, String> initMapUsers() {
        List<Users> users = accountService.getAllUser();
        Map<String, String> mapUsers = new HashMap<>();
        users.forEach(i -> {
            mapUsers.put(i.getId() + "", i.getUsername());
        });
        return mapUsers;
    }

    private Map<String, String> initMapRole() {
        List<Role> roles = accountService.getAllRole();
        Map<String, String> mapRole = new HashMap<>();
        roles.forEach(i -> {
            mapRole.put(i.getId() + "", i.getName());
        });
        return mapRole;
    }
}
