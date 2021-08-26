package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.controller.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SetLocale implements Command {
	private final static String LOCAL = "local";	
	
	private final static String REDIRECT_LAST_COMMAND_PATH = "Controller?command=";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();		
		session.setAttribute(LOCAL, request.getParameter(LOCAL));
		
		if (session.getAttribute(LAST_COMMAND_ATTRIBUTE) != null) {
			String lastCommand = (String) session.getAttribute(LAST_COMMAND_ATTRIBUTE);		
			response.sendRedirect(REDIRECT_LAST_COMMAND_PATH + lastCommand);
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}		
	}
}
