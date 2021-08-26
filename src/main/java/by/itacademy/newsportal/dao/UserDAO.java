package by.itacademy.newsportal.dao;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.User;

public interface UserDAO {
	
	void registrationUser(RegistrationInfo info) throws DAOException;
	
	User authorization(String login, String password) throws DAOException;
	
	boolean deleteUser(String login) throws DAOException;	

	boolean updateUser(RegistrationInfo info, String login) throws DAOException;

	RegistrationInfo takeUserInformation(String login) throws DAOException;
	
	RegistrationInfo takeUserInformation(int userId) throws DAOException;
}
