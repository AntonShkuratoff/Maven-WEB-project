package by.itacademy.newsportal.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itacademy.newsportal.bean.Comment;
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

public class GoToNewsPage implements Command {
	private static final Logger log = LogManager.getLogger(GoToNewsPage.class);
	
	private static final ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private static final NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String FORWARD_ADMIN_PAGE_PATH = "/WEB-INF/jsp/admin_news_page.jsp";
	private final static String FORWARD_USER_PAGE_PATH = "/WEB-INF/jsp/news_page.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";
	private final static String LOG_NOT_ALL_REQUEST_PARAMETERS = "Not all request parameters are available";

	private final static String COMMENTS_LIST_ATTRIBUTE = "commentsList";
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	
	private final static String USER = "user";
	private final static String NEWS = "news";
	private final static String ADMIN = "admin";
	private final static String NEWS_ID = "news_ID";
	private final static String FLAG = "flag";
	private final static String NEWS_TYPE = "news_type";	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		int newsId = -1;
		int userId = -1;
		String newsTipe = null;
		News news = null;
		User user = null;
		List<Comment> commentsList = new ArrayList<Comment>();
		HttpSession session = request.getSession();
		session.setAttribute(LAST_COMMAND_ATTRIBUTE, CommandName.GO_TO_NEWS_PAGE.toString());		

		if (request.getParameter(NEWS_ID) != null && 
			request.getParameter(NEWS_TYPE) != null && 
			session.getAttribute(USER) != null) 
		{
			newsId = Integer.valueOf(request.getParameter(NEWS_ID));
			user = (User) session.getAttribute(USER);
			userId = user.getId();
			newsTipe = request.getParameter(NEWS_TYPE);
		} else {
			log.error(LOG_NOT_ALL_REQUEST_PARAMETERS);
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		try {
			news = NEWS_SERVICE.getNewsByID(newsId, newsTipe);
			commentsList = NEWS_SERVICE.selectNewsComments(newsId);
			flag = NEWS_SERVICE.isFavorite(userId, newsId);
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		request.setAttribute(NEWS, news);
		request.setAttribute(COMMENTS_LIST_ATTRIBUTE, commentsList);
		request.setAttribute(FLAG, flag);

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
