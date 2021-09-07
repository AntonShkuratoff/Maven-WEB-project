package by.itacademy.newsportal.service.impl;

import java.util.List;

import by.itacademy.newsportal.bean.Comment;
import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.dao.DAOException;
import by.itacademy.newsportal.dao.DAOProvider;
import by.itacademy.newsportal.dao.NewsDAO;
import by.itacademy.newsportal.service.NewsService;
import by.itacademy.newsportal.service.ServiceException;

public class NewsServiceImpl implements NewsService {
	private static final DAOProvider DAO_PROVIDER = DAOProvider.getDaoProvider();
	private static final NewsDAO NEWS_DAO = DAO_PROVIDER.getNEWS_DAO();
	
	private final static String ERROR_UPDATE_NEWS_METHOD_SERVICE = "\nError in updateNews Service method.\n";
	private final static String ERROR_DELETE_NEWS_METHOD_SERVICE = "\nError in deleteNews Service method.\n";
	private final static String ERROR_GET_NEWS_BY_ID_METHOD_SERVICE = "\nError in getNewsById Service method.\n";
	private final static String ERROR_GET_LAST_NEWS_METHOD_SERVICE = "\nError in getLastNews Service method.\n";
	private final static String ERROR_ADD_NEWS_METHOD_SERVICE = "\nError in addNews Service method.\n";

	@Override
	public void addNews(News news, String role) throws ServiceException {
		try {
			NEWS_DAO.addNews(news, role);
		} catch (DAOException e) {
			throw new ServiceException(ERROR_ADD_NEWS_METHOD_SERVICE, e);
		}
	}

	@Override
	public boolean updateNews(News news, String role) throws ServiceException {
		try {
			return NEWS_DAO.updateNews(news, role);
		} catch (DAOException e) {
			throw new ServiceException(ERROR_UPDATE_NEWS_METHOD_SERVICE, e);
		}
	}

	@Override
	public int getNumberOfEntries() throws ServiceException {
		try {
			return NEWS_DAO.getNumberOfEntries();
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> getLastNews(int start, int total) throws ServiceException {

		try {
			return NEWS_DAO.getLastNews(start, total);
		} catch (DAOException e) {			
			throw new ServiceException(ERROR_GET_LAST_NEWS_METHOD_SERVICE, e);
		}
	}

	@Override
	public News getNewsByID(int newsID, String newsTipe) throws ServiceException {
		try {
			return NEWS_DAO.getNewsByID(newsID, newsTipe);
		} catch (DAOException e) {			
			throw new ServiceException(ERROR_GET_NEWS_BY_ID_METHOD_SERVICE, e);
		}
	}

	@Override
	public boolean deleteNews(int id, String newsTipe) throws ServiceException {
		try {
			return NEWS_DAO.deleteNews(id, newsTipe, null);
		} catch (DAOException e) {			
			throw new ServiceException(ERROR_DELETE_NEWS_METHOD_SERVICE, e);
		}
	}

	@Override
	public boolean commentNews(int userId, int newsId, String comment) throws ServiceException {
		try {
			return NEWS_DAO.commentNews(userId, newsId, comment);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Comment> selectNewsComments(int newsId) throws ServiceException {
		try {
			return NEWS_DAO.selectNewsComments(newsId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean deleteComment(int userId, int commentId) throws ServiceException {
		try {
			return NEWS_DAO.deleteComment(userId, commentId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean putNewsToFavorite(int userId, int newsId) throws ServiceException {
		try {
			return NEWS_DAO.putNewsToFavorite(userId, newsId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> selectNewsFromFavorite(int userId) throws ServiceException {
		try {
			return NEWS_DAO.selectNewsFromFavorite(userId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean isFavorite(int userId, int newsId) throws ServiceException {
		boolean flag = false;
		try {
			flag = NEWS_DAO.isFavorite(userId, newsId);
			return flag;
		} catch (DAOException e) {			
			return flag;
		}
	}

	@Override
	public boolean deleteNewsFromFavorite(int userId, int newsId) throws ServiceException {
		try {
			return NEWS_DAO.deleteNewsFromFavorite(userId, newsId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> getOfferedNews(String role, int userId) throws ServiceException {
		try {
			return NEWS_DAO.getOfferedNews(role, userId);
		} catch (DAOException e) {			
			throw new ServiceException(e);
		}
	}
}
