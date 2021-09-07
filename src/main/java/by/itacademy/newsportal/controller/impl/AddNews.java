package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.bean.Role;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddNews implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_SUCCESS_PAGE";
	
	private final static String NEWS_TITLE = "title";
	private final static String NEWS_BRIEF = "brief";
	private final static String NEWS_CONTENT = "content";
	private final static String ROLE = "role";
	private final static String USER_ID = "userId";
	private final static String NEWS_ID = "newsId";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = -1;
		int userId = -1;
		String userRole = null;
		String title = null;
		String brief = null;
		String content = null;
		News news = null;
		
		if (request.getParameter(ROLE) != null && 
			request.getParameter(USER_ID) != null && 
			request.getParameter(NEWS_TITLE) != null && 
			request.getParameter(NEWS_BRIEF) != null && 
			request.getParameter(NEWS_CONTENT) != null) 
		{
			userId = Integer.parseInt(request.getParameter(USER_ID));
			userRole = request.getParameter(ROLE);
			title = request.getParameter(NEWS_TITLE);
			brief = request.getParameter(NEWS_BRIEF);
			content = request.getParameter(NEWS_CONTENT);
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		if (request.getParameter(NEWS_ID) != null) {
			id = Integer.parseInt(request.getParameter(NEWS_ID));
			news = new News(id, userId, title, brief, content);
		} else {
			news = new News(userId, title, brief, content);
		}

		try {
			NEWS_SERVICE.addNews(news, userRole);
			response.sendRedirect(REDIRECT_SUCCESS_PATH);
		} catch (ServiceException e) {			
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
