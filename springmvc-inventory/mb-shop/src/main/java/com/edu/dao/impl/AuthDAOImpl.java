package com.edu.dao.impl;

import com.edu.dao.AuthDAO;
import com.edu.entity.Auth;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public class AuthDAOImpl extends BaseDAOImpl<Auth> implements AuthDAO<Auth> {
}
