package com.lobach.movielounge.servlet;

import com.lobach.movielounge.command.ActionCommand;
import com.lobach.movielounge.command.CommandManager;
import com.lobach.movielounge.exception.CommandException;
import com.lobach.movielounge.manager.PropertyManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.SessionContext;
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
        String page = PropertyManager.getProperty(BUNDLE_NAME, PROPERTY_MAIN_PAGE);
        try {
            ActionCommand command = CommandManager.defineCommand(request);
            RequestContent content = new RequestContent(request);
            logger.debug(content.getRequestParameter("command"));
            page = command.execute(content);
            content.passContent(request);
        } catch (CommandException e) {
            logger.error(String.format("Error occured during request processing: %s", e.getMessage()));
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
