package com.edu.validate;

import com.edu.entity.Users;
import com.edu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserValidator implements Validator {
    @Autowired
    private AccountService accountService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Users.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
        if (user.getId() == 0) {
            ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
            List<Users> users = accountService.findUserByProperty("username", user.getUsername());
            if (!users.isEmpty()) {
                errors.rejectValue("username", "msg.username.exist");
            }
        }
    }
}
