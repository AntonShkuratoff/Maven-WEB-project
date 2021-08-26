package by.itacademy.newsportal.dao;

import java.sql.Connection;
import java.util.List;

import by.itacademy.newsportal.bean.Comment;
import by.itacademy.newsportal.bean.News;

public interface NewsDAO {

	boolean addNews(News news, String role) throws DAOException;
	
	int getNumberOfEntries() throws DAOException;

	List<News> getLastNews(int start, int total) throws DAOException;
	
	News getNewsByID(int newsID, Connection connection) throws DAOException;

	News getNewsByID(int newsID, String newsTipe) throws DAOException;

	boolean updateNews(News news, String role) throws DAOException;

	boolean deleteNews(int id, String newsTipe, Connection connection) throws DAOException;

	boolean putNewsToFavorite(int userId, int newsId) throws DAOException;

	List<News> selectNewsFromFavorite(int userId) throws DAOException;
	
	boolean isFavorite(int userId, int newsId) throws DAOException;

	boolean deleteNewsFromFavorite(int userId, int newsId) throws DAOException;

	boolean commentNews(int userId, int newsId, String comment) throws DAOException;

	List<Comment> selectNewsComments(int newsId) throws DAOException;

	boolean deleteComment(int userId, int commentId) throws DAOException;
	
	List<News> getOfferedNews(String role, int userId) throws DAOException;
}
