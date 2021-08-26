package by.itacademy.newsportal.dao;

import by.itacademy.newsportal.dao.impl.NewsDAOImpl;
import by.itacademy.newsportal.dao.impl.UserDAOImpl;

public class DAOProvider {
	
	private static final DAOProvider DAO_PROVIDER = new DAOProvider();
	
	private final UserDAO USER_DAO = new UserDAOImpl();
	private final NewsDAO NEWS_DAO = new NewsDAOImpl();
	
	private DAOProvider() {}

	public static DAOProvider getDaoProvider() {
		return DAO_PROVIDER;
	}

	public UserDAO getUSER_DAO() {
		return USER_DAO;
	}

	public NewsDAO getNEWS_DAO() {
		return NEWS_DAO;
	}	
	
}
