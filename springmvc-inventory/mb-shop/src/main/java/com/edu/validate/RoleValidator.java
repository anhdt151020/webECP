package com.edu.validate;

import com.edu.entity.Role;
import com.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Role.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Role role = (Role) o;
        ValidationUtils.rejectIfEmpty(errors, "username", "msg.required");
        if (role.getId() != 0) {
            errors.rejectValue("name", "msg.name.exist");
        }
    }
}
