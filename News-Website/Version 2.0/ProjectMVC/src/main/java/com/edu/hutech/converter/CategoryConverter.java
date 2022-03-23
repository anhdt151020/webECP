package com.edu.hutech.converter;

import com.edu.hutech.dto.CategoryDTO;
import com.edu.hutech.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {
    public CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO result = new CategoryDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setCode(entity.getCode());
        return result;
    }
    public CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity result = new CategoryEntity();
        result.setName(dto.getName());
        result.setCode(dto.getCode());
        return result;
    }
}
