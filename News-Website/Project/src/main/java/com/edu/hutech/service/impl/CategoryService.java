package com.edu.hutech.service.impl;

import com.edu.hutech.dao.ICategoryDAO;
import com.edu.hutech.model.CategoryModel;
import com.edu.hutech.service.ICategoryService;

import javax.inject.Inject;
import java.util.List;

public class CategoryService implements ICategoryService {
    @Inject
    ICategoryDAO categoryDAO;
    @Override
    public List<CategoryModel> findAll() {
        return categoryDAO.findAll();
    }
}
