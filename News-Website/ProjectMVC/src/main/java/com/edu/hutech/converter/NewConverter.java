package com.edu.hutech.converter;

import com.edu.hutech.dto.NewDTO;
import com.edu.hutech.entity.NewEntity;
import org.springframework.stereotype.Component;

@Component
public class NewConverter {
    public NewDTO toDto(NewEntity entity) {
        NewDTO result = new NewDTO();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setShortDescription(entity.getShortDescription());
        result.setContent(entity.getContent());
        result.setThumbnail(entity.getThumbnail());
        result.setCategoryCode(entity.getCategory().getCode());
        return result;
    }
    //convert DTO > entity, đổ vào entity mới
    public NewEntity toEntity(NewDTO dto) {
        NewEntity result = new NewEntity();
        result.setTitle(dto.getTitle());
        result.setShortDescription(dto.getShortDescription());
        result.setContent(dto.getContent());
        result.setThumbnail(dto.getThumbnail());
        return result;
    }
    //convert DTO > entity, đổ vào entity cũ(update lại entity)
    public NewEntity toEntity(NewEntity result, NewDTO dto) {
        result.setTitle(dto.getTitle());
        result.setShortDescription(dto.getShortDescription());
        result.setContent(dto.getContent());
        result.setThumbnail(dto.getThumbnail());
        return result;
    }
}
