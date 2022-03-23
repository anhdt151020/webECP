package com.edu.hutech.controller.admin;

import com.edu.hutech.constant.SystemConstant;
import com.edu.hutech.model.NewModel;
import com.edu.hutech.paging.PageRequest;
import com.edu.hutech.paging.Pageble;
import com.edu.hutech.service.ICategoryService;
import com.edu.hutech.service.INewService;
import com.edu.hutech.sort.Sorter;
import com.edu.hutech.utils.FormUtil;
import com.edu.hutech.utils.MessageUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin-new"})
public class NewController extends HttpServlet {
    @Inject
    INewService newService;
    @Inject
    ICategoryService categoryService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewModel model = FormUtil.toModel(NewModel.class, req);    //get key-value từ url add vào model(chú ý tên param trùng vs tên trường trong model)

        String view = null;
        if (model.getType().equals(SystemConstant.LIST)) {
            Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(),model.getSortBy()));
            Integer offset = pageble.getOffset();
            model.setListResult(newService.findAll(pageble)); //newModel này chứa listResult
            model.setTotalItem(newService.getTotalItem());
            model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getMaxPageItem()));

            view = "/views/admin/new/list.jsp";
        } else if (model.getType().equals(SystemConstant.EDIT)) {
            if (model.getId() != null) {
                model = newService.findOne(model.getId());
            } else {

            }
            req.setAttribute("categories",categoryService.findAll());
            view = "/views/admin/new/edit.jsp";
        }
        MessageUtil.showMessage(req);
        req.setAttribute(SystemConstant.MODEL,model); /*truyền model để tầng present(view) xử lí*/
        RequestDispatcher rd = req.getRequestDispatcher(view);
        rd.forward(req, resp);

    }
}
