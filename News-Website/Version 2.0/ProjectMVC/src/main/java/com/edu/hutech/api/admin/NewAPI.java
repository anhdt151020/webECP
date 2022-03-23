package com.edu.hutech.api.admin;

import com.edu.hutech.dto.NewDTO;
import com.edu.hutech.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value = "newAPIOfAdmin")
public class NewAPI {
    @Autowired
    private INewService newService;

    @PostMapping("/api/new")
    public NewDTO createNew(@RequestBody NewDTO newDTO) {
        return newService.save(newDTO);
    }

    @PutMapping("/api/new")
    public NewDTO updateNew(@RequestBody NewDTO updateDTO) {
        return newService.save(updateDTO);
    }

    @DeleteMapping("/api/new")
    public void deleteNew(@RequestBody long[] ids) {
        newService.delete(ids);
    }
}
