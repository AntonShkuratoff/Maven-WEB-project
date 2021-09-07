package by.itacademy.newsportal.controller.impl;

import java.io.IOException;
import java.util.List;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.CommandName;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToUsersNews implements Command {
	private final static ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private final static NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String USERS_NEWS_PAGE_PATH = "/WEB-INF/jsp/users_news.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	
	private final static String NEWS_LIST_ATTRIBUTE = "newsList";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	
	private final static String USER_ID = "userId";
	private final static String USER_ROLE = "role";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String role = null;
		int userId = -1;
		List<News> newsList = null;
		request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_USERS_NEWS.toString());

		if (request.getParameter(USER_ID) != null && 
			request.getParameter(USER_ROLE) != null) 
		{
			userId = Integer.parseInt(request.getParameter(USER_ID));
			role = request.getParameter(USER_ROLE);
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			newsList = NEWS_SERVICE.getOfferedNews(role, userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		request.setAttribute(NEWS_LIST_ATTRIBUTE, newsList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(USERS_NEWS_PAGE_PATH);
		requestDispatcher.forward(request, response);
	}
}
