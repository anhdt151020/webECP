package com.edu.dao.impl;

import com.edu.dao.UserRoleDAO;
import com.edu.entity.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleDAOImpl extends BaseDAOImpl<UserRole> implements UserRoleDAO<UserRole> {
}
