package com.edu.controller;

import com.edu.entity.Role;
import com.edu.entity.Users;
import com.edu.model.Paging;
import com.edu.service.AccountService;
import com.edu.util.Constant;
import com.edu.validate.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleValidator roleValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == Users.class) {
            binder.setValidator(roleValidator);
        }
    }

    @RequestMapping(value = {"/role/list", "/role/list/"})
    public String moveListRole() {
        return "redirect:/role/list/1";
    }

    @RequestMapping(value = "/role/list/{page}")
    public String showRoleList(Model model, @ModelAttribute("searchForm") Role mRole, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<Role> roles = accountService.getAllRole(mRole, paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("roles", roles);
        return "role-list";
    }

    @GetMapping("/role/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Role");
        model.addAttribute("mRole", new Role());
        model.addAttribute("viewOnly", false);
        return "role-action";
    }

    @GetMapping("/role/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Role role = accountService.findRoleById(id);
        if (role != null) {
            model.addAttribute("titlePage", "Add Role");
            model.addAttribute("mRole", role);
            model.addAttribute("viewOnly", false);
            return "role-action";
        }
        return "redirect:/role/list";
    }

    @GetMapping("/role/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        Role role = accountService.findRoleById(id);
        if (role != null) {
            model.addAttribute("titlePage", "View Role");
            model.addAttribute("mRole", role);
            model.addAttribute("viewOnly", true);
            return "role-action";
        }
        return "redirect:/role/list";
    }

    @PostMapping("/role/save")
    public String save(Model model, @ModelAttribute("mRole") @Validated Role mRole, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mRole.getId() != 0) {
                model.addAttribute("titlePage", "Edit Role");
            } else {
                model.addAttribute("titlePage", "Add Role");
            }
            model.addAttribute("mRole", mRole);
            model.addAttribute("viewOnly", false);
            return "role-action";
        }
        if (mRole.getId() != 0) {
            try {
                accountService.updateRole(mRole);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                accountService.createRole(mRole);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/role/list";

    }

    @GetMapping("/role/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        Role role = accountService.findRoleById(id);
        if (role != null) {
            try {
                accountService.deleteRole(role);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            } catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/role/list";
    }
}
