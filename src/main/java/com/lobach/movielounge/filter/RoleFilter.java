package com.lobach.movielounge.filter;


import com.lobach.movielounge.model.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/control_servlet"}, servletNames = {"ControlServlet"})

public class RoleFilter implements Filter {
    private static final String USER_ROLE_ATTRIBUTE = "userRole";
    private static final String SERVLET_URL = "/control_servlet";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserRole roleType = (UserRole) session.getAttribute(USER_ROLE_ATTRIBUTE);
        if (roleType == null) {
            roleType = UserRole.GUEST;
            session.setAttribute(USER_ROLE_ATTRIBUTE, roleType);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(SERVLET_URL);
            dispatcher.forward(request, response);
            //return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
