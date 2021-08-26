package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.Controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogOut implements Command {
	private final static String USER = "user";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_MAIN_PAGE_PATH = "Controller?command=GO_TO_MAIN_PAGE&page=1";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if (session.getAttribute(USER) != null) {
			session.removeAttribute(USER);
			response.sendRedirect(REDIRECT_MAIN_PAGE_PATH);
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}