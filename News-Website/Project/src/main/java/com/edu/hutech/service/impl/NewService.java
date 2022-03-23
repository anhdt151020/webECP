package com.edu.hutech.service.impl;

import com.edu.hutech.dao.ICategoryDAO;
import com.edu.hutech.dao.INewDAO;
import com.edu.hutech.model.CategoryModel;
import com.edu.hutech.model.NewModel;
import com.edu.hutech.paging.Pageble;
import com.edu.hutech.service.INewService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class NewService implements INewService {
    @Inject
    private INewDAO newDAO;
    @Inject
    private ICategoryDAO categoryDAO;

    @Override
    public NewModel findOne(Long id) {
        NewModel newModel = newDAO.findOne(id);
        CategoryModel categoryModel = categoryDAO.findOne(newModel.getCategoryId());
        newModel.setCategoryCode(categoryModel.getCode());
        return newModel;
    }

    @Override
    public List<NewModel> findAll(Pageble pageble) {
        return newDAO.findAll(pageble);
    }

    @Override
    public List<NewModel> findByCategoryId(Long categoryId) {
        return newDAO.findByCategoryId(categoryId);
    }

    @Override
    public NewModel save(NewModel newModel) {
        /*Thêm 2 trường này cho newModel vì trên UI chưa set giá trị vào*/
        newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        newModel.setCreatedBy("");
        /*Trên UI chỉ set cateCode chứ chưa set cateId(db y.c set categoryId trong param query)*/
        CategoryModel categoryModel = categoryDAO.findOneByCode(newModel.getCategoryCode());
        newModel.setCategoryId(categoryModel.getId());
        /*Sử dụng xog hàm save thì db tự động create Id cho News*/
        Long newId = newDAO.save(newModel);
        return newDAO.findOne(newId);
    }

    @Override
    public NewModel update(NewModel updateNew) {
        /*Lấy 4 trường từ data cũ set vào model updateNew nhờ id = updateNew.getId()*/
        NewModel oldNew = newDAO.findOne(updateNew.getId());
        updateNew.setCreatedDate(oldNew.getCreatedDate());
        updateNew.setCreatedBy(oldNew.getCreatedBy());
        updateNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        updateNew.setModifiedBy("");
        /*Trên UI chỉ set cateCode chứ chưa set cateId(db y.c set categoryId trong param query)*/
        CategoryModel categoryModel = categoryDAO.findOneByCode(updateNew.getCategoryCode());
        updateNew.setCategoryId(categoryModel.getId());

        newDAO.update(updateNew);
        return newDAO.findOne(updateNew.getId());
    }

    @Override
    public void delete(long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            newDAO.delete(ids[i]);
        }
    }

    @Override
    public int getTotalItem() {
        return newDAO.getTotalItem();
    }
}
