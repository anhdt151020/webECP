package com.edu.hutech.controller.admin.api;

import com.edu.hutech.model.NewModel;
import com.edu.hutech.model.UserModel;
import com.edu.hutech.service.INewService;
import com.edu.hutech.utils.HttpUtil;
import com.edu.hutech.utils.SessionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api-admin-new"})
public class NewAPI extends HttpServlet {
    @Inject
    private INewService newService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel newModel = HttpUtil.of(req.getReader()).toModel(NewModel.class);
        UserModel userModel = (UserModel) SessionUtil.getInstance().getValue(req, "USERMODEL");
        newModel.setCreatedBy(userModel.getUserName());

        newModel = newService.save(newModel);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(),newModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel updateNew = HttpUtil.of(req.getReader()).toModel(NewModel.class);
        UserModel userModel = (UserModel) SessionUtil.getInstance().getValue(req, "USERMODEL");
        updateNew.setModifiedBy(userModel.getUserName());

        updateNew = newService.update(updateNew);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(),updateNew);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel newModel = HttpUtil.of(req.getReader()).toModel(NewModel.class);
        newService.delete(newModel.getIds());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(),"{}"); /*trả về cho client */
    }
}
