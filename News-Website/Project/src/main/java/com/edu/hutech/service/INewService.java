package com.edu.hutech.service;

import com.edu.hutech.model.NewModel;
import com.edu.hutech.paging.Pageble;

import java.util.List;

public interface INewService {
    public NewModel findOne(Long id);
    public List<NewModel> findAll(Pageble pageble);
    public List<NewModel> findByCategoryId(Long categoryId);
    public NewModel save(NewModel newModel);
    public NewModel update(NewModel updateNew);
    public void delete(long[] ids);
    public int getTotalItem();
}
