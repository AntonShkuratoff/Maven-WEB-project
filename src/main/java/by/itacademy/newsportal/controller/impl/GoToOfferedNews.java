package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

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

public class GoToOfferedNews implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String FORWARD_ADMIN_PAGE_PATH = "/WEB-INF/jsp/admin_offered_news_redactor.jsp";
	private final static String FORWARD_USER_PAGE_PATH = "/WEB-INF/jsp/user_offered_news_redactor.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	
	private final static String USER = "user";
	private final static String NEWS = "news";
	private final static String ADMIN = "admin";
	private final static String NEWS_ID = "news_ID";
	private final static String NEWS_TIPE = "news_tipe";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int news_ID = -1;
		String newsTipe = null;
		News news = null;
		User user = null;
		HttpSession session = request.getSession();
		session.setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_OFFERED_NEWS.toString());

		if (request.getParameter(NEWS_ID) != null && 
			request.getParameter(NEWS_TIPE) != null && 
			session.getAttribute(USER) != null) 
		{
			news_ID = Integer.valueOf(request.getParameter(NEWS_ID));
			user = (User) session.getAttribute(USER);
			newsTipe = request.getParameter(NEWS_TIPE);
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			news = NEWS_SERVICE.getNewsByID(news_ID, newsTipe);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		request.setAttribute(NEWS, news);

		String path = null;
		if (ADMIN.equalsIgnoreCase(user.getRole().toString())) {
			path = FORWARD_ADMIN_PAGE_PATH;
		} else {
			path = FORWARD_USER_PAGE_PATH;
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
		requestDispatcher.forward(request, response);
	}
}
