package com.edu.hutech.controller.admin;

import com.edu.hutech.dto.NewDTO;
import com.edu.hutech.service.ICategoryService;
import com.edu.hutech.service.INewService;
import com.edu.hutech.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller(value = "newControllerOfAdmin")
public class NewController {

    @Autowired
    private INewService newService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private MessageUtils messageUtils;

    @RequestMapping(value = "/admin/news/list", method = RequestMethod.GET)
    public ModelAndView showList(@RequestParam("page") int page,
                                 @RequestParam("limit") int limit,
                                 HttpServletRequest request) {

        Pageable pageable =new PageRequest((page-1),limit);
        NewDTO model = new NewDTO();
        model.setListResult(newService.findAll(pageable));
        model.setPage(page);
        model.setLimit(limit);
        model.setTotalItem(newService.getTotalItem());
        //số trang = tổng item / limit
        int math = (int) Math.ceil((double) model.getTotalItem() / model.getLimit());
        model.setTotalPage(math);

        ModelAndView mav = new ModelAndView("admin/new/list");
        mav.addObject("model",model);

        //add alert
        if (request.getParameter("message") != null) {
            Map<String, String> mapMessage = messageUtils.mapMessage(request.getParameter("message"));
            mav.addObject("message", mapMessage.get("message"));
            mav.addObject("alert", mapMessage.get("alert"));
        }
        return mav;
    }

    @RequestMapping(value = "/admin/news/edit", method = RequestMethod.GET)
    //với required=false ta check nếu id=.. thì lấy param id
    public ModelAndView editNew(@RequestParam(value = "id", required = false) Long id, HttpServletRequest request) {
        NewDTO model = new NewDTO();
        if (id != null) {
            model = newService.findById(id);
        }

        ModelAndView mav = new ModelAndView("admin/new/edit");
        mav.addObject("categories",categoryService.findAll());
        mav.addObject("model", model);

        //add alert
        if (request.getParameter("message") != null) {
            Map<String, String> mapMessage = messageUtils.mapMessage(request.getParameter("message"));
            mav.addObject("message", mapMessage.get("message"));
            mav.addObject("alert", mapMessage.get("alert"));
        }
        return mav;
    }
}
