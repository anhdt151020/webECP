package com.edu.hutech.utils;

import com.edu.hutech.constant.SystemConstant;

import javax.servlet.http.HttpServletRequest;

public class MessageUtil {
    public static void showMessage(HttpServletRequest req) {
        String message = req.getParameter("message");
        if (message != null) {
            String messageResponse = null;
            String alert = null;
            if (message.equals("insert_success")) {
                messageResponse = SystemConstant.INSERT_SUCCESS;
                alert = SystemConstant.ALERT_SUCCESS;
            } else if (message.equals("update_success")) {
                messageResponse = SystemConstant.UPDATE_SUCCESS;
                alert = SystemConstant.ALERT_SUCCESS;
            } else if (message.equals("delete_success")) {
                messageResponse = SystemConstant.DELETE_SUCCESS;
                alert = SystemConstant.ALERT_SUCCESS;
            } else if (message.equals("error_system")) {
                messageResponse = SystemConstant.ERROR_SYSTEM;
                alert = SystemConstant.ALERT_DANGER;
            }
            req.setAttribute("messageResponse", messageResponse);
            req.setAttribute("alert",alert);
        }
    }
}
