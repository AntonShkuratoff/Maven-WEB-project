package by.itacademy.newsportal.service;

import java.util.List;

import by.itacademy.newsportal.bean.Comment;
import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.bean.Role;
import by.itacademy.newsportal.dao.DAOException;

public interface NewsService {
	
	void addNews(News news, String role) throws ServiceException;
	
	boolean updateNews(News news, String role) throws ServiceException;
	
	int getNumberOfEntries() throws ServiceException;
	
	List<News> getLastNews(int start, int total) throws ServiceException;
	
	News getNewsByID(int newsID, String newsTipe) throws ServiceException;

	boolean deleteNews(int id, String newsTipe) throws ServiceException;
	
	boolean commentNews(int userId, int newsId, String comment) throws ServiceException;
	
	List<Comment> selectNewsComments(int newsId) throws ServiceException;
	
	boolean deleteComment(int userId, int commentId) throws ServiceException;
	
	boolean putNewsToFavorite(int userId, int newsId) throws ServiceException;
	
	List<News> selectNewsFromFavorite(int userId) throws ServiceException;
	
	boolean isFavorite(int userId, int newsId) throws ServiceException;
	
	boolean deleteNewsFromFavorite(int userId, int newsId) throws ServiceException;
	
	List<News> getOfferedNews(String role, int userId) throws ServiceException;
}
