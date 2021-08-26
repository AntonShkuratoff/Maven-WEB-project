package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.CommandName;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToSuccessPage implements Command{
	private final String FORWARD_PATH = "/WEB-INF/jsp/success.jsp";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_SUCCESS_PAGE.toString());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
		requestDispatcher.forward(request, response);		
	}
}