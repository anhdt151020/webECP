package com.edu.hutech.service.impl;

import com.edu.hutech.converter.CategoryConverter;
import com.edu.hutech.dto.CategoryDTO;
import com.edu.hutech.entity.CategoryEntity;
import com.edu.hutech.repository.CategoryRepository;
import com.edu.hutech.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Map<String, String> findAll() {
        Map<String, String> models = new HashMap<>();
        List<CategoryEntity> entities = categoryRepository.findAll();
        for (CategoryEntity item: entities) {
            models.put(item.getCode(),item.getName());
        }
        return models;
    }
}
