package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateNews implements Command {
	private static final Logger log = LogManager.getLogger(UpdateNews.class);
	
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_SUCCESS_PAGE";
	private final static String LOG_NOT_ALL_REQUEST_PARAMETERS = "Not all request parameters are available";
	
	private final static String NEWS_TITLE = "title";
	private final static String NEWS_BRIEF = "brief";
	private final static String NEWS_CONTENT = "content";
	private final static String NEWS_ID = "newsId";
	private final static String USER_ID = "userId";
	private final static String ROLE = "role";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int newsId = -1;
		int userId = -1;
		News news = null;
		String role = null;
		String title = null;
		String brief = null;
		String content = null;

		if (request.getParameter(NEWS_TITLE) != null && 
			request.getParameter(NEWS_BRIEF) != null && 
			request.getParameter(NEWS_CONTENT) != null &&
			request.getParameter(ROLE) != null && 
			request.getParameter(NEWS_ID) != null &&
			request.getParameter(USER_ID) != null) 
		{
			title = request.getParameter(NEWS_TITLE);
			brief = request.getParameter(NEWS_BRIEF);
			content = request.getParameter(NEWS_CONTENT);
			role = request.getParameter(ROLE);
			newsId = Integer.parseInt(request.getParameter(NEWS_ID));
			userId = Integer.parseInt(request.getParameter(USER_ID));
			news = new News(newsId, userId, title, brief, content);
		} else {
			log.error(LOG_NOT_ALL_REQUEST_PARAMETERS);
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			NEWS_SERVICE.updateNews(news, role);
			response.sendRedirect(REDIRECT_SUCCESS_PATH);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
