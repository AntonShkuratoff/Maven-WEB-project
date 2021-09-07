package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PutNewsToFavorite implements Command{
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_NEWS_PAGE&news_ID=";
	
	private final static String USER_ID = "userId";
	private final static String NEWS_ID = "newsId";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = -1;
		int newsId = -1;
		
		if(request.getParameter(USER_ID) != null && request.getParameter(NEWS_ID) != null) {
			userId = Integer.parseInt(request.getParameter(USER_ID));
			newsId = Integer.parseInt(request.getParameter(NEWS_ID));
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}
		
		try {
			NEWS_SERVICE.putNewsToFavorite(userId, newsId);
			response.sendRedirect(REDIRECT_SUCCESS_PATH + newsId);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}		
	}
}
