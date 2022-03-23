package com.edu.service;

import com.edu.entity.Category;
import com.edu.model.Paging;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    List<Category> getAllCategory(Category category, Paging paging);

    List<Category> findCategoryByProperty(String property, Object value);

    Category findCategoryById(int id);

    void createCategory(Category category) throws Exception;

    void updateCategory(Category category) throws Exception;

    void deleteCategory(Category category) throws Exception;
}
