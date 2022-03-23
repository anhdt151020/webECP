package com.edu.controller;

import com.edu.entity.Role;
import com.edu.entity.Users;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.util.Constant;
import com.edu.validate.UserValidator;
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
public class UsersController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserValidator userValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == Users.class) {
            binder.setValidator(userValidator);
        }
    }

    @RequestMapping(value = {"/user/list", "/user/list/"})
    public String moveListUsers() {
        return "redirect:/user/list/1";
    }

    @RequestMapping(value = "/user/list/{page}")
    public String showUsersList(Model model, @ModelAttribute("searchForm") Users mUsers, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(10);
        paging.setIndexPage(page);
        List<Users> users = accountService.getAllUser(mUsers, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Users");
        model.addAttribute("mUsers", new Users());
        model.addAttribute("viewOnly", false);
        return "user-action";
    }

    @GetMapping("/user/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Users users = accountService.findUsersById(id);
        if (users != null) {
            model.addAttribute("titlePage", "Add Users");
            model.addAttribute("mUsers", users);
            model.addAttribute("viewOnly", false);
            return "user-action";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/user/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        Users users = accountService.findUsersById(id);
        if (users != null) {
            model.addAttribute("titlePage", "View Users");
            model.addAttribute("mUsers", users);
            model.addAttribute("viewOnly", true);
            return "user-action";
        }
        return "redirect:/user/list";
    }

    @PostMapping("/user/save")
    public String save(Model model, @ModelAttribute("mUsers") @Validated Users mUsers, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mUsers.getId() != 0) {
                model.addAttribute("titlePage", "Edit Users");
            } else {
                model.addAttribute("titlePage", "Add Users");
            }
            model.addAttribute("mUsers", mUsers);
            model.addAttribute("viewOnly", false);
            return "user-action";
        }
        if (mUsers.getId() != 0) {
            try {
                accountService.updateUsers(mUsers);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                accountService.createUsers(mUsers);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/user/list";

    }

    @GetMapping("/user/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        Users users = accountService.findUsersById(id);
        if (users != null) {
            try {
                accountService.deleteUsers(users);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/user/list";
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
