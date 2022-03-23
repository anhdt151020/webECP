package com.edu.service.impl;

import com.edu.dao.CategoryDAO;
import com.edu.entity.Category;
import com.edu.model.Paging;
import com.edu.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    final static Logger log = Logger.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryDAO<Category> categoryDAO;

    public List<Category> getAllCategory() {
        log.info("[Service::" + "find all Category]");
        return categoryDAO.findAll();
    }

    public List<Category> getAllCategory(Category category, Paging paging) {
        log.info("[Service::" + "find Category by params]");
        Map<String,Object> mapParams = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        try {
            if (category.getId() != 0) {
                sql.append(" and model.id=:id");
                mapParams.put("id", category.getId());
            }
            if (!StringUtils.isBlank(category.getCode())) {
                sql.append(" and model.code like :code");
                mapParams.put("code", "%" + category.getCode() + "%");
            }
            if (!StringUtils.isBlank(category.getName())) {
                sql.append(" and model.name like :name");
                mapParams.put("name", "%" + category.getName() + "%");
            }
        } catch (Exception e) {
            mapParams = null;
        }
        return categoryDAO.findAll(sql.toString(), mapParams, paging);
    }

    public List<Category> findCategoryByProperty(String property, Object value) {
        log.info("[Service::" + "find Category by property]");
        return categoryDAO.findByProperty(property, value);
    }

    public Category findCategoryById(int id) {
        log.info("[Service::" + "find Category by id]");
        return categoryDAO.findById(Category.class, id);
    }

    public void createCategory(Category category) throws Exception {
        log.info("[Service::" + "create Category]");
        category.setActiveFlag(1);
        category.setCreatedDate(new Date());
        category.setModifiedDate(new Date());
        categoryDAO.save(category);
    }

    public void updateCategory(Category category) throws Exception {
        log.info("[Service::" + "update Category]");
        category.setModifiedDate(new Date());

        Category oldCate = categoryDAO.findById(Category.class, category.getId());
        category.setCreatedDate(oldCate.getCreatedDate());
        category.setActiveFlag(oldCate.getActiveFlag());
        categoryDAO.update(category);
    }

    public void deleteCategory(Category category) throws Exception {
        log.info("[Service::" + "delete Category]");

        Category oldCate = categoryDAO.findById(Category.class, category.getId());
        oldCate.setModifiedDate(new Date());
        oldCate.setActiveFlag(0);
        categoryDAO.update(oldCate);
    }
}
