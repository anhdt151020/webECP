package com.edu.hutech.filter;

import com.edu.hutech.constant.SystemConstant;
import com.edu.hutech.model.UserModel;
import com.edu.hutech.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //chuyen qua httpservlet
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI(); //get dc uri vd "/admin/..."
        if (url.startsWith("/admin")) {
            UserModel model = (UserModel) SessionUtil.getInstance().getValue(request,"USERMODEL");
            if (model != null) {
                if (model.getRole().getCode().equals(SystemConstant.ADMIN)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (model.getRole().getCode().equals(SystemConstant.USER)) {
                    response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=not_permission&alert=warning");
                }
            } else {
                response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=not_login&alert=info");
            }
        } else {
            filterChain.doFilter(servletRequest,servletResponse); // khi no care về chuyện filter ta sẽ ko filter
        }
    }

    @Override
    public void destroy() {

    }
}
