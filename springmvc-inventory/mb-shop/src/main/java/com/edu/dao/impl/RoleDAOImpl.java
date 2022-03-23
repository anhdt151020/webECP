package com.edu.dao.impl;

import com.edu.dao.RoleDAO;
import com.edu.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO<Role> {
}
