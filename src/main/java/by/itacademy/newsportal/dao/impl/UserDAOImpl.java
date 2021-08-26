package by.itacademy.newsportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.Role;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.dao.DAOException;
import by.itacademy.newsportal.dao.UserDAO;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPool;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPoolException;

public class UserDAOImpl implements UserDAO {
	private static ConnectionPool connectionPool = null;

	private final static String REGISTRATE_NEW_USER_SQL = "INSERT INTO users(surname, name, patronymic, birthday, login, password, email) VALUES(?,?,?,?,?,?,?)";
	private final static String AUTHORIZATION_USER_SQL = "SELECT * FROM users WHERE login=? AND password=?";
	private final static String DELETE_USER_BY_LOGIN_SQL = "DELETE FROM users WHERE login=?";
	private final static String UPDATE_USER_SQL = "UPDATE users SET surname=?, name=?, patronymic=?, birthday=?, login=?, password=?, email=? WHERE login=?";
	private final static String TAKE_USER_INFORMATION_BY_LOGIN_SQL = "SELECT * FROM users WHERE login=?";
	private final static String TAKE_USER_INFORMATION_BY_ID_SQL = "SELECT * FROM users WHERE id=?";

	private final static String ERROR_PREPEAR_STATEMENT = "\nError executing the Prepear Statement";
	private final static String ERROR_CONNECTION_POOL = "\nError in connectionPool";
	private final static String ERROR_RESULT_SET = "\nError with Result Set";
	private final static String REG_USER_METHOD_DAO = " in registrationUser DAO method.\n";
	private final static String AUTH_USER_METHOD_DAO = " in authorization DAO method.\n";
	private final static String DELETE_USER_METHOD_DAO = " in deleteUser DAO method.\n";
	private final static String UPDATE_USER_METHOD_DAO = " in updateUser DAO method.\n";
	private final static String TAKE_USER_INFORM_METHOD_DAO = " in takeUserInformation DAO method.\n";

	private final static String ID = "id";
	private final static String LOGIN = "login";
	private final static String ROLE = "role";
	private final static String SURNAME = "surname";
	private final static String NAME = "name";
	private final static String PATRONYMIC = "patronymic";
	private final static String EMAIL = "email";
	private final static String BIRTHDAY = "birthday";

	@Override
	public void registrationUser(RegistrationInfo info) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(REGISTRATE_NEW_USER_SQL);
			ps.setString(1, info.getSurname());
			ps.setString(2, info.getName());
			ps.setString(3, info.getPatronymic());
			ps.setString(4, info.getBirthday());
			ps.setString(5, info.getLogin());
			ps.setString(6, info.getPassword());
			ps.setString(7, info.getEmail());
			ps.executeUpdate();

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
	}

	@Override
	public User authorization(String login, String password) throws DAOException {
		User user = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(AUTHORIZATION_USER_SQL);
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();
			int id;
			String log;
			String role;

			while (rs.next()) {
				id = rs.getInt(ID);
				log = rs.getString(LOGIN);
				role = rs.getString(ROLE).toUpperCase();
				user = new User(id, log, Role.valueOf(role));
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
		return user;
	}

	@Override
	public boolean deleteUser(String login) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(DELETE_USER_BY_LOGIN_SQL);
			ps.setString(1, login);
			flag = ps.executeUpdate() > 0;
			return flag;
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
	}

	@Override
	public boolean updateUser(RegistrationInfo info, String login) throws DAOException {
		boolean flag;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(UPDATE_USER_SQL);
			ps.setString(8, login);
			ps.setString(1, info.getSurname());
			ps.setString(2, info.getName());
			ps.setString(3, info.getPatronymic());
			ps.setString(4, info.getBirthday());
			ps.setString(5, info.getLogin());
			ps.setString(6, info.getPassword());
			ps.setString(7, info.getEmail());
			flag = ps.executeUpdate() > 0;
			return flag;
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
	}

	@Override
	public RegistrationInfo takeUserInformation(String login) throws DAOException {
		RegistrationInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(TAKE_USER_INFORMATION_BY_LOGIN_SQL);
			ps.setString(1, login);
			rs = ps.executeQuery();
			String surname;
			String name;
			String patronymic;
			String birthday;
			String log;
			String email;

			while (rs.next()) {
				surname = rs.getString(SURNAME);
				name = rs.getString(NAME);
				patronymic = rs.getString(PATRONYMIC);
				birthday = rs.getString(BIRTHDAY);
				log = rs.getString(LOGIN);
				email = rs.getString(EMAIL);
				info = new RegistrationInfo(surname, name, patronymic, birthday, log, email);
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
		return info;
	}

	@Override
	public RegistrationInfo takeUserInformation(int userId) throws DAOException {
		RegistrationInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.takeConnection();
			ps = con.prepareStatement(TAKE_USER_INFORMATION_BY_ID_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				String surname = rs.getString(SURNAME);
				String name = rs.getString(NAME);
				String patronymic = rs.getString(PATRONYMIC);
				String birthday = rs.getString(BIRTHDAY);
				String log = rs.getString(LOGIN);
				String email = rs.getString(EMAIL);
				info = new RegistrationInfo(surname, name, patronymic, birthday, log, email);
			}
		} catch (ConnectionPoolException | SQLException e) {
			// logging
			throw new DAOException(ERROR_CONNECTION_POOL + TAKE_USER_INFORM_METHOD_DAO, e);
		} finally {
			try {
				connectionPool.closeConnection(con, ps, rs);
			} catch (ConnectionPoolException e) {
				// logging
				throw new DAOException(e);
			}
		}
		return info;
	}
}
