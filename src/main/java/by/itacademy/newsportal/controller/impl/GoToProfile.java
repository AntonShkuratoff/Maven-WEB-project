package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.CommandName;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import by.itacademy.newsportal.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToProfile implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final UserService USER_SERVICE = PROVIDER.getUserService();
	
	private final String FORWARD_PATH = "/WEB-INF/jsp/profile.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	private final static String REGISTRATION_INFO_ATTRIBUTE = "registrationInfo";
	
	private final static String USER = "user";
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		String login = "";
		RegistrationInfo info = null;
		HttpSession session = request.getSession();
		session.setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_PROFILE.toString().toString());

		if (session.getAttribute(USER) != null) {
			user = (User) session.getAttribute(USER);
			login = user.getLogin();
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}
		
		try {
			info = USER_SERVICE.takeUserInformation(login);
			request.setAttribute(REGISTRATION_INFO_ATTRIBUTE, info);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
			requestDispatcher.forward(request, response);
		} catch (ServiceException e) {
			// logging
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}		
	}
}
