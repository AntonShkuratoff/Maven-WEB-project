package by.itacademy.newsportal.controller.impl;

import java.io.IOException;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.controller.Command;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteNews implements Command {
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String REDIRECT_SUCCESS_PATH = "Controller?command=GO_TO_SUCCESS_PAGE";
	
	private final static String NEWS_ID = "newsId";
	private final static String NEWS_TIPE = "news_tipe";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		int newsId = -1;
		String newsTipe= null;		
		
		if (request.getParameter(NEWS_TIPE) != null && 
			request.getParameter(NEWS_ID) != null ) 
		{
			newsTipe = request.getParameter(NEWS_TIPE);
			newsId = Integer.parseInt(request.getParameter(NEWS_ID));
		} else {
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			NEWS_SERVICE.deleteNews(newsId, newsTipe);
			response.sendRedirect(REDIRECT_SUCCESS_PATH);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);			
		}
	}
}
