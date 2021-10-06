package by.itacademy.newsportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.User;
import by.itacademy.newsportal.dao.DAOException;
import by.itacademy.newsportal.dao.DAOProvider;
import by.itacademy.newsportal.dao.UserDAO;
import by.itacademy.newsportal.service.ServiceException;
import by.itacademy.newsportal.service.UserService;

public class UserServiceImpl implements UserService {
	private static final DAOProvider DAO_PROVIDER = DAOProvider.getDaoProvider();
	private static final UserDAO USER_DAO = DAO_PROVIDER.getUSER_DAO();	
	
	private final static String INCORRECT_PARAMETER = "You entered an incorrect ";
	
	private final static String LOGIN = "login";
	private final static String PASSWORD = "password";
	private final static String SURNAME = "surname";
	private final static String NAME = "name";
	private final static String PATRONYMIC = "patronymic";
	private final static String EMAIL = "email";
	private final static String BIRTHDAY = "birthday";	
	private final static String DOT = ".";	
	
	private final static String FULL_NAME_REGEX = "[a-zA-ZА-Яа-яЁё]{2,25}";
	private final static String BIRTHDAY_REGEX = "\\d{1,2}\\.\\d{1,2}\\.\\d{4}";
	private final static String LOGIN_REGEX = "[a-zA-Z0-9]{2,20}";
	private final static String PASSWORD_REGEX = "[a-zA-Z0-9]{5,20}";
	private final static String EMAIL_REGEX = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
	

	@Override
	public List<String> registraition(RegistrationInfo info) throws ServiceException {
		List<String> err = init(info);
		if (!err.isEmpty()) {
			return err;
		}
		try {
			USER_DAO.registrationUser(info);
			return err;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<String> updateUser(RegistrationInfo info, String login) throws ServiceException {
		List<String> err = init(info);	
		if (!err.isEmpty()) {
			return err;
		}
		try {
			USER_DAO.updateUser(info, login);
			return null;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<String> init(RegistrationInfo info) {
		List<String> err = new ArrayList<String>();
		err.add(validate(FULL_NAME_REGEX, info.getSurname(), SURNAME));
		err.add(validate(FULL_NAME_REGEX, info.getName(), NAME));
		err.add(validate(FULL_NAME_REGEX, info.getPatronymic(), PATRONYMIC));
		err.add(validate(BIRTHDAY_REGEX, info.getBirthday(), BIRTHDAY));
		err.add(validate(LOGIN_REGEX, info.getLogin(), LOGIN));
		err.add(validate(PASSWORD_REGEX, info.getPassword(), PASSWORD));
		err.add(validate(EMAIL_REGEX, info.getEmail(), EMAIL));
		while (err.remove(null)) {}
		return err;	
	}

	@Override
	public RegistrationInfo takeUserInformation(String login) throws ServiceException {
		try {
			return USER_DAO.takeUserInformation(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private String validate(String regex, String userParam, String paramName) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userParam);
		boolean check = matcher.matches();
		if (!check) {
			return INCORRECT_PARAMETER + paramName + DOT;
		} else {
			return null;
		}
	}

	@Override
	public User authorization(String login, String password) throws ServiceException {
		List<String> err = new ArrayList<String>();
		err.add(validate(LOGIN_REGEX, login, LOGIN));
		err.add(validate(PASSWORD_REGEX, password, PASSWORD));
		while (err.remove(null)) {}
		if (!err.isEmpty()) {
			throw new ServiceException();
		}
		try {
			return USER_DAO.authorization(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
