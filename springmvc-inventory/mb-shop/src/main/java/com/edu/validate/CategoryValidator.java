package com.edu.validate;

import com.edu.entity.Category;
import com.edu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CategoryValidator implements Validator {
    @Autowired
    private CategoryService categoryService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Category.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category category = (Category) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "msg.required");

        if (category.getCode() != null) {
            List<Category> rs = categoryService.findCategoryByProperty("code", category.getCode());
            if (!rs.isEmpty()) {
                if (category.getId() != rs.get(0).getId()) { //neu tim ra 1 cate trung code != current-cate >> error
                    errors.rejectValue("code", "msg.code.exist");
                }
            }
        }
    }
}
