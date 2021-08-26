package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.CommandName;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToAuthorization implements Command {
	private final static String FORWARD_PATH = "/WEB-INF/jsp/authorization.jsp";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.AUTHORIZATION.toString());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
		requestDispatcher.forward(request, response);
	}
}
