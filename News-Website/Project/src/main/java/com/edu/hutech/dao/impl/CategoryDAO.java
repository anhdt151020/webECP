package com.edu.hutech.dao.impl;

import com.edu.hutech.dao.ICategoryDAO;
import com.edu.hutech.mapper.impl.CategoryMapper;
import com.edu.hutech.model.CategoryModel;

import java.util.List;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {
    @Override
    public CategoryModel findOne(Long id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        List<CategoryModel> list = query(sql, new CategoryMapper(), id);
        return (list.isEmpty() == true) ? null : list.get(0);
    }

    @Override
    public CategoryModel findOneByCode(String code) {
        String sql = "SELECT * FROM category WHERE code = ?";
        List<CategoryModel> list = query(sql, new CategoryMapper(),code);
        return (list.isEmpty() == true) ? null : list.get(0);
    }

    @Override
    public List<CategoryModel> findAll() {
        String sql = "SELECT * FROM category";
        return query(sql, new CategoryMapper());
    }

    @Override
    public Long save(CategoryModel newModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO category (name, code, createddate, createby) ");
        sql.append("VALUES (?,?,?,?)");
        return insert(sql.toString(), newModel.getName(), newModel.getCode(),
                newModel.getCreatedDate(), newModel.getCreatedBy());
    }

    @Override
    public void update(CategoryModel updateCategory) {
        StringBuilder sql = new StringBuilder("UPDATE category SET name = ?, code = ?, ");
        sql.append("createddate = ?, modifieddate = ?, createdby = ?, modifiedby = ? ");
        sql.append("WHERE id = ?");
        update(sql.toString(), updateCategory.getName(), updateCategory.getCode(),
                updateCategory.getCreatedDate(), updateCategory.getCreatedBy(),
                updateCategory.getModifiedDate(), updateCategory.getModifiedBy(),
                updateCategory.getId());
    }
}
