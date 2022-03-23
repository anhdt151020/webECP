package com.edu.hutech.service;

import com.edu.hutech.dto.NewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    public NewDTO save(NewDTO dto);
    public void delete(long[] ids);
    public List<NewDTO> findAll(Pageable pageable);
    public int totalItem();
}
