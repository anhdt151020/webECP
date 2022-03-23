package com.edu.hutech.dao;

import com.edu.hutech.model.NewModel;
import com.edu.hutech.paging.Pageble;

import java.util.List;

public interface INewDAO extends GenericDAO<NewModel> {
    public List<NewModel> findAll(Pageble pageble);
    public NewModel findOne(Long id);
    public List<NewModel> findByCategoryId(Long categoryId);
    public Long save(NewModel newModel);
    public void update(NewModel updateNew);
    public void delete(long id);
    public int getTotalItem();
}
