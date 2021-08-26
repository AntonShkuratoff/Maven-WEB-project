package by.itacademy.newsportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import by.itacademy.newsportal.bean.Comment;
import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.dao.DAOException;
import by.itacademy.newsportal.dao.NewsDAO;
import by.itacademy.newsportal.dao.UserDAO;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPool;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPoolException;

public class NewsDAOImpl implements NewsDAO {
	private static ConnectionPool connectionPool = null;
	private static final UserDAO USER_DAO = new UserDAOImpl();

	private final static String ADD_NEWS_SQL = "INSERT INTO news(title, brief, content, date, user_id) VALUES(?,?,?,?,?)";
	private final static String GET_NUMBER_OF_ENTRIES_SQL = "SELECT COUNT(id) AS NumberOfEntries FROM news";
	private final static String GET_LAST_NEWS_SQL = "SELECT * FROM news ORDER BY id DESC LIMIT ";
	private final static String GET_ALL_USERS_NEWS_SQL = "SELECT * FROM news_from_users";
	private final static String GET_PERSONAL_USERS_NEWS_SQL = "SELECT * FROM news_from_users WHERE user_id=?";
	private final static String GET_PUBLISHED_NEWS_BY_ID_SQL = "SELECT * FROM news WHERE id=?";
	private final static String GET_OFFERED_NEWS_BY_ID_SQL = "SELECT * FROM news_from_users WHERE id=?";
	private final static String DELETE_PUBLISHED_NEWS_BY_ID_SQL = "DELETE FROM news WHERE id=?";
	private final static String DELETE_OFFERED_NEWS_BY_ID_SQL = "DELETE FROM news_from_users WHERE id=?";
	private final static String UPDATE_PUBLISHED_NEWS_SQL = "UPDATE news SET title=?, brief=?, content=? WHERE id=?";
	private final static String UPDATE_OFFERED_NEWS_SQL = "UPDATE news_from_users SET title=?, brief=?, content=? WHERE id=?";
	private final static String PUT_NEWS_TO_FAVORITE_SQL = "INSERT INTO favorite_news(user_id, news_id) VALUES(?,?)";
	private final static String SELECT_NEWS_FROM_FAVORITE_SQL = "SELECT * FROM favorite_news WHERE user_id=?";
	private final static String IS_NEWS_FAVORITE_SQL = "SELECT * FROM favorite_news WHERE user_id=? AND news_id=?";
	private final static String DELETE_NEWS_FROM_FAVORITE_SQL = "DELETE FROM favorite_news WHERE user_id=? AND news_id=?";
	private final static String COMMENT_NEWS_SQL = "INSERT INTO comments(user_id, news_id, content, date) VALUES(?,?,?,?)";
	private final static String SELECT_COMMENTS_BY_NEWS_ID_SQL = "SELECT * FROM comments WHERE news_id=?";
	private final static String DELETE_COMMENT_SQL = "DELETE FROM comments WHERE id=? AND user_id=?";
	private final static String SUGGEST_NEWS_SQL = "INSERT INTO news_from_users(title, brief, content, date, user_id) VALUES(?,?,?,?,?)";

	private final static String ID = "id";
	private final static String NEWS_ID = "news_id";
	private final static String USER_ID = "user_id";
	private final static String TITLE = "title";
	private final static String BRIEF = "brief";
	private final static String CONTENT = "content";
	private final static String DATE = "date";
	private final static String COMMA = ",";
	private final static String NUMBER_OF_ENTRIES = "NumberOfEntries";
	private final static String ROLE_ADMIN = "ADMIN";
	private final static String ROLE_USER = "USER";
	private final static String ROLE_USER_ADMIN = "USER_ADMIN";
	private final static String NEWS_TIPE_OFFERED = "offered";
	private final static String NEWS_TIPE_PUBLISHED = "published";

	@Override
	public boolean addNews(News news, String role) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			if (ROLE_ADMIN.equals(role)) {
				ps = con.prepareStatement(ADD_NEWS_SQL);
			} else if (ROLE_USER.equals(role)) {
				ps = con.prepareStatement(SUGGEST_NEWS_SQL);
			} else if (ROLE_USER_ADMIN.equals(role)) {
				ps = con.prepareStatement(ADD_NEWS_SQL);
			}
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getBrief());
			ps.setString(3, news.getContent());
			ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(5, news.getUserId());
			flag = ps.executeUpdate() > 0;
			deleteNews(news.getId(), NEWS_TIPE_OFFERED, con);
		} catch (SQLException | ConnectionPoolException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public int getNumberOfEntries() throws DAOException {
		int numberOfEntries = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(GET_NUMBER_OF_ENTRIES_SQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				numberOfEntries = rs.getInt(NUMBER_OF_ENTRIES);
			}

		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return numberOfEntries;
	}

	@Override
	public List<News> getLastNews(int start, int total) throws DAOException {
		List<News> newsList = new ArrayList<News>();
		News news;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(GET_LAST_NEWS_SQL + start + COMMA + total);
			rs = ps.executeQuery();
			int id;
			String title;
			String brief;
			String content;
			String date;

			while (rs.next()) {
				id = rs.getInt(ID);
				title = rs.getString(TITLE);
				brief = rs.getString(BRIEF);
				content = rs.getString(CONTENT);
				date = rs.getString(DATE);
				news = new News(id, title, brief, content, date);
				newsList.add(news);
			}

		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return newsList;
	}

	@Override
	public News getNewsByID(int newsID, Connection connection) throws DAOException {
		News news = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (connection == null) {
				connectionPool = ConnectionPool.getInstance();
				con = connectionPool.takeConnection();
			} else {
				con = connection;
			}

			ps = con.prepareStatement(GET_PUBLISHED_NEWS_BY_ID_SQL);
			ps.setInt(1, newsID);
			rs = ps.executeQuery();
			int id;
			int userId;
			String title;
			String brief;
			String content;
			String date;

			while (rs.next()) {
				id = rs.getInt(ID);
				userId = rs.getInt(USER_ID);
				title = rs.getString(TITLE);
				brief = rs.getString(BRIEF);
				content = rs.getString(CONTENT);
				date = rs.getString(DATE);
				news = new News(id, userId, title, brief, content, date);
			}

		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			if (connection == null) {
				try {
					connectionPool.closeConnection(con, ps, rs);
				} catch (ConnectionPoolException e) {
					// logging
					throw new DAOException(e);
				}
			} else {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					// logging
					throw new DAOException(e);
				}
			}

		}
		return news;
	}

	@Override
	public News getNewsByID(int newsID, String newsTipe) throws DAOException {
		News news = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			if (NEWS_TIPE_PUBLISHED.equals(newsTipe)) {
				ps = con.prepareStatement(GET_PUBLISHED_NEWS_BY_ID_SQL);
			} else if (NEWS_TIPE_OFFERED.equals(newsTipe)) {
				ps = con.prepareStatement(GET_OFFERED_NEWS_BY_ID_SQL);
			}

			ps.setInt(1, newsID);
			rs = ps.executeQuery();
			int id;
			int userId;
			String title;
			String brief;
			String content;
			String date;

			while (rs.next()) {
				id = rs.getInt(ID);
				userId = rs.getInt(USER_ID);
				title = rs.getString(TITLE);
				brief = rs.getString(BRIEF);
				content = rs.getString(CONTENT);
				date = rs.getString(DATE);
				news = new News(id, userId, title, brief, content, date);
			}
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return news;
	}

	@Override
	public boolean deleteNews(int id, String newsTipe, Connection connection) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			if (connection == null) {
				connectionPool = ConnectionPool.getInstance();
				con = connectionPool.takeConnection();
			} else {
				con = connection;
			}

			if (NEWS_TIPE_PUBLISHED.equals(newsTipe)) {
				ps = con.prepareStatement(DELETE_PUBLISHED_NEWS_BY_ID_SQL);
			} else if (NEWS_TIPE_OFFERED.equals(newsTipe)) {
				ps = con.prepareStatement(DELETE_OFFERED_NEWS_BY_ID_SQL);
			} else {
				// logging
				throw new DAOException();
			}

			ps.setInt(1, id);
			flag = ps.executeUpdate() > 0;
			ps.close();
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			if (connection == null) {
				try {
					connectionPool.closeConnection(con, ps);
				} catch (ConnectionPoolException e) {
					// logging
					throw new DAOException(e);
				}
			} else {
				try {
					ps.close();
				} catch (SQLException e) {
					// logging
					throw new DAOException(e);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean updateNews(News news, String role) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			if (ROLE_ADMIN.equals(role)) {
				ps = con.prepareStatement(UPDATE_PUBLISHED_NEWS_SQL);
				ps.setInt(4, news.getId());
				ps.setString(1, news.getTitle());
				ps.setString(2, news.getBrief());
				ps.setString(3, news.getContent());
			} else if (ROLE_USER.equals(role)) {
				ps = con.prepareStatement(UPDATE_OFFERED_NEWS_SQL);
				ps.setInt(4, news.getId());
				ps.setString(1, news.getTitle());
				ps.setString(2, news.getBrief());
				ps.setString(3, news.getContent());
			}

			flag = ps.executeUpdate() > 0;
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public boolean putNewsToFavorite(int userId, int newsId) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(PUT_NEWS_TO_FAVORITE_SQL);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			flag = ps.executeUpdate() > 0;
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			// Add method name to error
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public List<News> selectNewsFromFavorite(int userId) throws DAOException {
		List<News> newsList = new ArrayList<News>();
		News news;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(SELECT_NEWS_FROM_FAVORITE_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				news = getNewsByID(rs.getInt(NEWS_ID), con);
				newsList.add(news);
			}
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return newsList;
	}

	@Override
	public boolean isFavorite(int userId, int newsId) throws DAOException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(IS_NEWS_FAVORITE_SQL);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			rs = ps.executeQuery();
			flag = rs.next();
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public boolean deleteNewsFromFavorite(int userId, int newsId) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(DELETE_NEWS_FROM_FAVORITE_SQL);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			flag = ps.executeUpdate() > 0;
			ps.close();
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public boolean commentNews(int userId, int newsId, String comment) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(COMMENT_NEWS_SQL);
			ps.setInt(1, userId);
			ps.setInt(2, newsId);
			ps.setString(3, comment);
			ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			flag = ps.executeUpdate() > 0;
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public List<Comment> selectNewsComments(int newsId) throws DAOException {
		List<Comment> commentList = new ArrayList<Comment>();
		Comment comment = null;
		RegistrationInfo registrationInfo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(SELECT_COMMENTS_BY_NEWS_ID_SQL);
			ps.setInt(1, newsId);
			rs = ps.executeQuery();
			int id;
			int userId;
			String userLogin;
			String content;
			String date;

			while (rs.next()) {
				id = rs.getInt(ID);
				userId = rs.getInt(USER_ID);
				content = rs.getString(CONTENT);
				date = rs.getString(DATE);
				registrationInfo = USER_DAO.takeUserInformation(userId);
				userLogin = registrationInfo.getLogin();
				comment = new Comment(id, userId, newsId, userLogin, content, date);
				commentList.add(comment);
			}
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return commentList;
	}

	@Override
	public boolean deleteComment(int userId, int commentId) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(DELETE_COMMENT_SQL);
			ps.setInt(1, commentId);
			ps.setInt(2, userId);
			flag = ps.executeUpdate() > 0;
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return flag;
	}

	@Override
	public List<News> getOfferedNews(String role, int userId) throws DAOException {
		List<News> newsList = new ArrayList<News>();
		News news;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();

			if (ROLE_ADMIN.equals(role)) {
				ps = con.prepareStatement(GET_ALL_USERS_NEWS_SQL);
			} else if (ROLE_USER.equals(role)) {
				ps = con.prepareStatement(GET_PERSONAL_USERS_NEWS_SQL);
				ps.setInt(1, userId);
			}
			rs = ps.executeQuery();
			int id;
			String title;
			String brief;
			String content;
			String date;

			while (rs.next()) {
				id = rs.getInt(ID);
				title = rs.getString(TITLE);
				brief = rs.getString(BRIEF);
				content = rs.getString(CONTENT);
				date = rs.getString(DATE);
				news = new News(id, title, brief, content, date);
				newsList.add(news);
			}
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return newsList;
	}
}
