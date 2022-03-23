package com.edu.validate;

import com.edu.entity.UserRole;
import com.edu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserRoleValidator implements Validator {
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == UserRole.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRole userRole = (UserRole) o;
        UserRole searchForm = new UserRole();
        searchForm.setUserId(userRole.getUserId());
        searchForm.setRoleId(userRole.getRoleId());
        List<UserRole> userRoleListActive = permissionService.getAllUserRole(searchForm,null);
        if (!CollectionUtils.isEmpty(userRoleListActive)) {
            errors.rejectValue("userId", "msg.userrole.exist");
            errors.rejectValue("roleId", "msg.userrole.exist");
        }
    }
}
