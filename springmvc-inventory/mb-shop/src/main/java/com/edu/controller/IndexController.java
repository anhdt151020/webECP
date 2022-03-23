package com.edu.controller;

import com.edu.entity.Role;
import com.edu.entity.UserRole;
import com.edu.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

//@Controller
//@Transactional(rollbackFor = Exception.class)
public class IndexController {
//    @Autowired
//    SessionFactory mySessionFactory;
//
//    @GetMapping("index2")
//    public String index(){
//        Session session = mySessionFactory.getCurrentSession();
//
//        List<Users> ulist =session.createQuery("from Users ").getResultList();
//        for (Users item: ulist) {
//            System.out.println("users " + item.getEmail());
//        }
//        List<Role> rList =session.createQuery("from Role ").getResultList();
//        for (Role item: rList) {
//            System.out.println("roles " + item.getName());
//        }
//        List<UserRole> urlist =session.createQuery("from UserRole ").getResultList();
//        for (UserRole item: urlist) {
//            System.out.println("ur " + item.getRoleId());
//        }
//        System.out.println("-------");
//        return "index";
//    }
//
//    @GetMapping("getall")
//    public String getAll(){
//        StringBuilder queryString = new StringBuilder("");
//        queryString.append(" from ").append("com.edu.entity.Users").append(" as model where model.activeFlag=1");
//        List<Users> ulist = mySessionFactory.getCurrentSession().createQuery(queryString.toString()).list();
//        for (Users item : ulist) {
//            System.out.println("users " + item.getEmail());
//        }
//        return "index";
//    }
//
//    @GetMapping("insertins")
//    public String insertIns(){
//        Date dt = new Date();
//        dt.setTime(1634774400);
//        Users instance = new Users();
//        instance.setUsername("test");
//        instance.setPassword("test");
//        instance.setName("test");
//        instance.setActiveFlag(1);
//        instance.setCreatedDate(new Date());
//        instance.setModifiedDate(dt);
//        mySessionFactory.getCurrentSession().persist(instance);
//        return "index";
//    }
//
//    @GetMapping("updateins/{id}")
//    public String updateIns(@PathVariable int id) {
//        Date dt = new Date();
//        dt.setTime(1634774400);
//        Users instance = new Users();
//        instance.setId(id);
//        instance.setUsername("test");
//        instance.setPassword("update");
//        instance.setName("update");
//        instance.setActiveFlag(1);
//        instance.setCreatedDate(new Date());
//        instance.setModifiedDate(dt);
//        mySessionFactory.getCurrentSession().merge(instance);
//        return "index";
//    }
//
//
//
}
