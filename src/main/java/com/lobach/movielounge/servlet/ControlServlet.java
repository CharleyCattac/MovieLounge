package com.lobach.movielounge.servlet;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.CommandManager;
import com.lobach.movielounge.util.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/control_servlet")
@MultipartConfig()

public class ControlServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_NAME = "config";
    private static final String PROPERTY_MAIN_PAGE = "path.main_page";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionCommand command = CommandManager.defineCommand(request);
        RequestContent content = new RequestContent(request);
        Router executionResult = command.execute(content);
        String page = executionResult.url;
        Router.RouteType routeType = executionResult.getRouteType();
        content.passContent(request);

        switch (routeType) {
            case FORWARD: {
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
                break;
            }
            case REDIRECT: {
                response.sendRedirect(page);
                break;
            }
        }
    }
}
