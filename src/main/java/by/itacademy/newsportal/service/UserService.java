package by.itacademy.newsportal.service;

import java.util.List;

import by.itacademy.newsportal.bean.RegistrationInfo;
import by.itacademy.newsportal.bean.User;

public interface UserService {

	List<String> registraition(RegistrationInfo info) throws ServiceException;

	User authorization(String login, String password) throws ServiceException;

	List<String> updateUser(RegistrationInfo info, String login) throws ServiceException;

	RegistrationInfo takeUserInformation(String login) throws ServiceException;

}
