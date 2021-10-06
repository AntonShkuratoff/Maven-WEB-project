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

public class GoToMainPage implements Command {
	private final static ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private final static NewsService NEWS_SERVICE = PROVIDER.getNewsService();
	
	private final static String USER_MAIN_PAGE_PATH = "/WEB-INF/jsp/user_main_page.jsp";
	private final static String ADMIN_MAIN_PAGE_PATH = "/WEB-INF/jsp/admin_main_page.jsp";
	private final static String MAIN_PAGE_PATH = "/WEB-INF/jsp/main.jsp";
	private final static String REDIRECT_UNKNOWN_COMMAND_PATH = "Controller?command=UNKNOWN_COMMAND";	
	
	private final static String USER = "user";
	private final static String ADMIN = "admin";
	private final static String PAGE = "page";
	private final static String LAST_COMMAND_VALUE = "GO_TO_MAIN_PAGE&page=";
	
	private final static String LAST_COMMAND_ATTRIBUTE = "lastCommand";
	private final static String NEWS_LIST_ATTRIBUTE = "newsList";
	private final static String NUMBER_OF_PAGES_ATTRIBUTE = "numberOfPages";
	
	private final static int TOTAL = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<News> newsList = null;
		int numberOfEntries = -1;
		int numberOfPages = -1;
		int pageNumber = -1;
		int startWith = 0;
		HttpSession session = request.getSession();		

		try {
			numberOfEntries = NEWS_SERVICE.getNumberOfEntries();
		} catch (ServiceException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}

		numberOfPages = (int) Math.ceil((double) numberOfEntries / TOTAL);		

		try {
			if (request.getParameter(PAGE) != null) {
				pageNumber = Integer.parseInt(request.getParameter(PAGE));
			}
			startWith = (pageNumber - 1) * TOTAL;			
			newsList = NEWS_SERVICE.getLastNews(startWith, TOTAL);
		} catch (ServiceException | NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
			return;
		}		
		
		request.setAttribute(NEWS_LIST_ATTRIBUTE, newsList);		
		request.setAttribute(NUMBER_OF_PAGES_ATTRIBUTE, numberOfPages);
		session.setAttribute(LAST_COMMAND_ATTRIBUTE, LAST_COMMAND_VALUE + pageNumber);
		
		User user = null;
		String role = "";

		if (session.getAttribute(USER) != null) {
			user = (User) session.getAttribute(USER);
			role = user.getRole().toString();
		}
		try {
			RequestDispatcher requestDispatcher = null;
			if (USER.equalsIgnoreCase(role)) {
				requestDispatcher = request.getRequestDispatcher(USER_MAIN_PAGE_PATH);
			} else if (ADMIN.equalsIgnoreCase(role)) {
				requestDispatcher = request.getRequestDispatcher(ADMIN_MAIN_PAGE_PATH);
			} else {
				requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_PATH);
			}
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		} catch (IOException e) {
			e.printStackTrace();
			response.sendRedirect(REDIRECT_UNKNOWN_COMMAND_PATH);
		}
	}
}
