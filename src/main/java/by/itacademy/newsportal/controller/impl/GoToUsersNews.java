package by.itacademy.newsportal.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import jakarta.servlet.http.HttpSession;

public class GoToUsersNews implements Command {
	private static final Logger log = LogManager.getLogger(GoToUsersNews.class);
	
	private final static ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private final static NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String USERS_NEWS_PAGE_PATH = "/WEB-INF/jsp/users_news.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String LOG_NOT_ALL_REQUEST_PARAMETERS = "Not all request parameters are available";
	
	private final static String NEWS_LIST_ATTRIBUTE = "newsList";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	
	private final static String USER_ATTRIBUTE = "user";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		HttpSession session = request.getSession();
		String role = null;
		int userId = -1;
		List<News> newsList = null;
		request.getSession().setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_USERS_NEWS.toString());

		if (session.getAttribute(USER_ATTRIBUTE) != null) {
			user = (User) session.getAttribute(USER_ATTRIBUTE);
			userId = user.getId();
			role = user.getRole().toString();
		} else {
			log.error(LOG_NOT_ALL_REQUEST_PARAMETERS);
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
