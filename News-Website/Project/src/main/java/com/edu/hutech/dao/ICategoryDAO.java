package com.edu.hutech.dao;

import com.edu.hutech.model.CategoryModel;

import java.util.List;

public interface ICategoryDAO extends GenericDAO<CategoryModel>{
    public CategoryModel findOne(Long id);
    public CategoryModel findOneByCode(String code);
    public List<CategoryModel> findAll();
    public Long save(CategoryModel newModel);
    public void update(CategoryModel updateCategory);
}
