package com.edu.hutech.converter;

import com.edu.hutech.dto.NewDTO;
import com.edu.hutech.entity.NewEntity;
import org.springframework.stereotype.Component;

@Component
public class NewConverter {
    public NewDTO toDto(NewEntity entity) {
        NewDTO dto = new NewDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setTitle(entity.getTitle());
        dto.setShortDescription(entity.getShortDescription());
        dto.setContent(entity.getContent());
        dto.setThumbnail(entity.getThumbnail());
        dto.setCategoryCode(entity.getCategories().getCode());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    //convert DTO > entity, đổ vào entity mới
    public NewEntity toEntity(NewDTO dto) {
        NewEntity entity = new NewEntity();
        entity.setTitle(dto.getTitle());
        entity.setShortDescription(dto.getShortDescription());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }
    //convert DTO > entity, đổ vào entity cũ(update lại entity)
    public NewEntity toEntity(NewEntity entity, NewDTO dto) {
        entity.setTitle(dto.getTitle());
        entity.setShortDescription(dto.getShortDescription());
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }
}
