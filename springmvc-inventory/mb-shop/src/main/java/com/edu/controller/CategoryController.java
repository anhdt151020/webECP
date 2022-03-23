package com.edu.controller;

import com.edu.entity.Category;
import com.edu.model.Paging;
import com.edu.service.CategoryService;
import com.edu.util.Constant;
import com.edu.validate.CategoryValidator;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryValidator categoryValidator;
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (binder.getTarget().getClass() == Category.class) {
            binder.setValidator(categoryValidator);
        }
    }

    @RequestMapping(value = {"/category/list", "/category/list/"})
    public String moveListCategory() {
        return "redirect:/category/list/1";
    }
    @RequestMapping(value = "/category/list/{page}")
    public String showCategoryList(Model model, @ModelAttribute("searchForm") Category mCategory, @PathVariable("page") int page, HttpSession session) {
        Paging paging = new Paging(5);
        paging.setIndexPage(page);
        List<Category> categories = categoryService.getAllCategory(mCategory,paging);
        if (session.getAttribute(Constant.MSG_SUCCESS) != null) {
            model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
            session.removeAttribute(Constant.MSG_SUCCESS);
        }
        if (session.getAttribute(Constant.MSG_ERROR) != null) {
            model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
            session.removeAttribute(Constant.MSG_ERROR);
        }
        model.addAttribute("pageInfo", paging);
        model.addAttribute("categories", categories);
        return "category-list";
    }

    @GetMapping("/category/add")
    public String add(Model model) {
        model.addAttribute("titlePage", "Add Category");
        model.addAttribute("mCategory", new Category());
        model.addAttribute("viewOnly", false);
        return "category-action";
    }

    @GetMapping("/category/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        Category category = categoryService.findCategoryById(id);
        if (category != null) {
            model.addAttribute("titlePage", "Edit Category");
            model.addAttribute("mCategory", category);
            model.addAttribute("viewOnly", false);
            return "category-action";
        }
        return "redirect:/category/list";
    }

    @GetMapping("/category/view/{id}")
    public String view(Model model, @PathVariable("id") int id) {
        Category category = categoryService.findCategoryById(id);
        if (category != null) {
            model.addAttribute("titlePage", "View Category");
            model.addAttribute("mCategory", category);
            model.addAttribute("viewOnly", true);
            return "category-action";
        }
        return "redirect:/category/list";
    }

    @PostMapping("/category/save")
    public String save(Model model, @ModelAttribute("mCategory") @Validated Category mCategory, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            if (mCategory.getId() != 0) {
                model.addAttribute("titlePage", "Edit Category");
            }else {
                model.addAttribute("titlePage", "Add Category");
            }
            model.addAttribute("mCategory", mCategory);
            model.addAttribute("viewOnly", false);
            return "category-action";
        }
        if (mCategory.getId() != 0) {
            try {
                categoryService.updateCategory(mCategory);
                session.setAttribute(Constant.MSG_SUCCESS, "Update success");
            }catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Update fail");
            }
        } else {
            try {
                categoryService.createCategory(mCategory);
                session.setAttribute(Constant.MSG_SUCCESS, "Insert success");
            }catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Insert fail");
            }
        }
        return "redirect:/category/list";

    }

    @GetMapping("/category/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id, HttpSession session) {
        Category category = categoryService.findCategoryById(id);
        if (category != null) {
            try {
                categoryService.deleteCategory(category);
                session.setAttribute(Constant.MSG_SUCCESS, "Delete success");
            }catch (Exception e) {
                session.setAttribute(Constant.MSG_ERROR, "Delete fail");
            }
        }
        return "redirect:/category/list";
    }
}
