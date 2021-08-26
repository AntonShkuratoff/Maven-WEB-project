package by.itacademy.newsportal.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.controller.CommandName;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToFavorite implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";	
	private final static String FORWARD_PATH = "/WEB-INF/jsp/favorite_news_page.jsp";	
	
	private final static String USER = "user";
	private final static String FAVORITE_NEWS_LIST = "newsList";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		int userId = -1;
		User user = null;
		List<News> newsList = new ArrayList<News>();
		request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_FAVORITE.toString());

		if (request.getSession().getAttribute(USER) != null) {
			user = (User) request.getSession().getAttribute(USER);
			userId = user.getId();
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			newsList = NEWS_SERVICE.selectNewsFromFavorite(userId);
			request.setAttribute(FAVORITE_NEWS_LIST, newsList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
			requestDispatcher.forward(request, response);
		} catch (ServiceException e) {
			// logging
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
