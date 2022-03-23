package com.edu.hutech.util;

import com.edu.hutech.dto.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {
    public static MyUser getPrincipal() {
        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUser;
    }
    public static List<String> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

        List<String> results = new ArrayList<>();
        for (GrantedAuthority authority: authorities) {
            results.add(authority.getAuthority());
        }
        return results;
    }
}
