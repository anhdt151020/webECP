package com.edu.dao.impl;

import com.edu.dao.UsersDAO;
import com.edu.entity.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UsersDAOImpl extends BaseDAOImpl<Users> implements UsersDAO<Users> {
}
