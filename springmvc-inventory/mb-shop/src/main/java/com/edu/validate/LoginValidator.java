package com.edu.validate;

import com.edu.entity.Users;
import com.edu.service.AccountService;
import com.edu.util.HashingPassword;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class LoginValidator implements Validator {
    @Autowired
    private AccountService accountService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Users.class;
    } // just support for Users

    @Override
    public void validate(Object o, Errors errors) {

        // check empty input
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
        ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
        //check user exist
        Users user = (Users) o;
        if (!StringUtils.isBlank(user.getUsername()) && !StringUtils.isBlank(user.getPassword())) {
            List<Users> users = accountService.findUserByProperty("username", user.getUsername());
            if (!users.isEmpty()) {
                if (!users.get(0).getPassword().equals(HashingPassword.encrypt(user.getPassword()))) {
                    errors.rejectValue("password", "msg.wrong.password");
                }
            } else {
                errors.rejectValue("username", "msg.wrong.username");
            }
        }
    }
}
