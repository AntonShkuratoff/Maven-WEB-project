package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SendComment implements Command {
	private static final Logger log = LogManager.getLogger(SendComment.class);
	
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_NEWS_PAGE&news_type=published&news_ID=";
	private final static String LOG_NOT_ALL_REQUEST_PARAMETERS = "Not all request parameters are available";
	
	private final static String USER_ID = "userId";
	private final static String NEWS_ID = "newsId";
	private final static String CONTENT = "content";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = -1;
		int newsId = -1;
		String content = null;

		if (request.getParameter(USER_ID) != null && 
			request.getParameter(NEWS_ID) != null && 
			request.getParameter(CONTENT) != null) 
		{
			userId = Integer.parseInt(request.getParameter(USER_ID));
			newsId = Integer.parseInt(request.getParameter(NEWS_ID));
			content = request.getParameter(CONTENT);
		} else {
			log.error(LOG_NOT_ALL_REQUEST_PARAMETERS);
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			NEWS_SERVICE.commentNews(userId, newsId, content);
			response.sendRedirect(REDIRECT_SUCCESS_PATH + newsId);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
