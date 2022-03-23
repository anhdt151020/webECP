package com.edu.hutech.service.impl;

import com.edu.hutech.constant.SystemConstant;
import com.edu.hutech.dto.MyUser;
import com.edu.hutech.entity.RoleEntity;
import com.edu.hutech.entity.UserEntity;
import com.edu.hutech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserNameAndStatus(username, SystemConstant.ACTIVE_STATUS);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //put trường role vào
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity roleItem : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(roleItem.getCode()));
        }

        MyUser myUser = new MyUser(userEntity.getUserName(), userEntity.getPassword(), true, true, true, true, authorities);
        myUser.setFullName(userEntity.getFullName());

        return myUser;
    }
}
