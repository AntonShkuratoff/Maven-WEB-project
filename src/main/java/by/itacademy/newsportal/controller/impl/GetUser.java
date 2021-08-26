package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import by.itacademy.newsportal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GetUser implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final UserService USER_SERVICE = PROVIDER.getUserService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_SUCCESS_PAGE";
	
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String USER = "user";
	
	private final static int SESSION_MAX_INACTIVE_INTERVAL = 120;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String login = null;
		String password = null;
		User user = null;
		HttpSession session = request.getSession();

		if (request.getParameter(LOGIN) != null && 
			request.getParameter(PASSWORD) != null) 
		{
			login = request.getParameter(LOGIN);
			password = request.getParameter(PASSWORD);
		} else {
			// logging
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			user = USER_SERVICE.authorization(login, password);			
			session.setAttribute(USER, user);
			session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
			response.sendRedirect(REDIRECT_SUCCESS_PATH);
		} catch (ServiceException e) {
			// logging
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
