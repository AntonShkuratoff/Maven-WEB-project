package by.itacademy.newsportal.controller.impl;

import java.io.IOException;
import java.util.List;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import by.itacademy.newsportal.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateUser implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final UserService USER_SERVICE = PROVIDER.getUserService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_SUCCESS_PAGE";
	private final static String FORWARD_UNKNOWN_COMMAND_PAGE_PATH = "/WEB-INF/jsp/unknown_command_page.jsp";
	
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String SURNAME = "surname";
	private final static String NAME = "name";
	private final static String PATRONYMIC = "patronymic";
	private final static String EMAIL = "email";	
	private final static String DAY = "day";
	private final static String MONTH = "month";
	private final static String YEAR = "year";
	private final static String DOT = ".";
	private final static String USER = "user";
	private final static String ERROR_LIST_ATTRIBUTE = "errorList";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> err = null;
		HttpSession session = request.getSession();
		User user = null;
		RegistrationInfo info = null;
		String surname = null;
		String name = null;
		String patronymic = null;
		String birthday = null;
		String login = null;
		String oldLogin = null;
		String password = null;
		String email = null;		
		
		if (request.getParameter(SURNAME) != null &&
			request.getParameter(NAME) != null &&
			request.getParameter(PATRONYMIC) != null &&
			request.getParameter(DAY) != null &&
			request.getParameter(MONTH) != null &&
			request.getParameter(YEAR) != null &&
			request.getParameter(LOGIN) != null &&
			request.getParameter(PASSWORD) != null &&
			request.getParameter(EMAIL) != null &&
			session.getAttribute(USER) != null)
			
		{
			surname = request.getParameter(SURNAME);
			name = request.getParameter(NAME);
			patronymic = request.getParameter(PATRONYMIC);
			birthday = request.getParameter(DAY) + DOT + request.getParameter(MONTH) + DOT + request.getParameter(YEAR);
			login = request.getParameter(LOGIN);
			password = request.getParameter(PASSWORD);
			email = request.getParameter(EMAIL);
			info = new RegistrationInfo(surname, name, patronymic, birthday, login, password, email);
			user = (User) session.getAttribute(USER);
			oldLogin = user.getLogin();
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}		
		try {
			err = USER_SERVICE.updateUser(info, oldLogin);
			if (!err.isEmpty()) {
				request.setAttribute(ERROR_LIST_ATTRIBUTE, err);
				RequestDispatcher dispatcher = request.getRequestDispatcher(FORWARD_UNKNOWN_COMMAND_PAGE_PATH);				
				dispatcher.forward(request, response);				
			}
			user.setLogin(login);
			session.setAttribute(USER, user);
			response.sendRedirect(REDIRECT_SUCCESS_PATH);
		} catch (ServiceException | ServletException | IOException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}		
	}
}
