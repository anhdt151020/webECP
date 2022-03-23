package com.edu.hutech.api;

import com.edu.hutech.dto.NewDTO;
import com.edu.hutech.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewAPI {
    @Autowired
    private INewService newService;

    @PostMapping(value = "/new")
    public NewDTO createNew(@RequestBody NewDTO model) {
        return newService.save(model);
    }
    @PutMapping(value = "/new/{id}") //{id} trùng vs tên @..("id") ở dưới
    public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return newService.save(model);
    }
    @DeleteMapping(value = "/new")
    public void deleteNew(@RequestBody long[] ids) {
        newService.delete(ids);
    }
}
