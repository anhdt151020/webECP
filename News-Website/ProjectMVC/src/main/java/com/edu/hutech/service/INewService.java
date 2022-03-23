package com.edu.hutech.service;

import com.edu.hutech.dto.NewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    public List<NewDTO> findAll(Pageable pageable);
    public int getTotalItem();
    public NewDTO findById(long id);
    public NewDTO save(NewDTO dto);
    public void delete(long[] ids);
}
