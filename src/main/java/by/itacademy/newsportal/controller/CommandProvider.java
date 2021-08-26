package by.itacademy.newsportal.controller;

import java.util.HashMap;
import java.util.Map;

import by.itacademy.newsportal.controller.impl.AddNews;
import by.itacademy.newsportal.controller.impl.DeleteComment;
import by.itacademy.newsportal.controller.impl.DeleteNews;
import by.itacademy.newsportal.controller.impl.DeleteNewsFromFavorite;
import by.itacademy.newsportal.controller.impl.GetUser;
import by.itacademy.newsportal.controller.impl.GoToAuthorization;
import by.itacademy.newsportal.controller.impl.GoToFavorite;
import by.itacademy.newsportal.controller.impl.GoToMainPage;
import by.itacademy.newsportal.controller.impl.GoToNewsPage;
import by.itacademy.newsportal.controller.impl.GoToNewsRedactor;
import by.itacademy.newsportal.controller.impl.GoToOfferedNews;
import by.itacademy.newsportal.controller.impl.GoToProfile;
import by.itacademy.newsportal.controller.impl.GoToRegistration;
import by.itacademy.newsportal.controller.impl.GoToSuccessPage;
import by.itacademy.newsportal.controller.impl.GoToUnknownCommandPage;
import by.itacademy.newsportal.controller.impl.GoToUsersNews;
import by.itacademy.newsportal.controller.impl.LogOut;
import by.itacademy.newsportal.controller.impl.PutNewsToFavorite;
import by.itacademy.newsportal.controller.impl.RegistrationNewUser;
import by.itacademy.newsportal.controller.impl.SendComment;
import by.itacademy.newsportal.controller.impl.SetLocale;
import by.itacademy.newsportal.controller.impl.UpdateNews;
import by.itacademy.newsportal.controller.impl.UpdateUser;

public class CommandProvider {
	
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.AUTHORIZATION, new GoToAuthorization());
		commands.put(CommandName.REGISTRATION, new GoToRegistration());
		commands.put(CommandName.UNKNOWN_COMMAND, new GoToUnknownCommandPage());
		commands.put(CommandName.REGISTRAITION_NEW_USER, new RegistrationNewUser());
		commands.put(CommandName.GET_USER, new GetUser());
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
		commands.put(CommandName.SET_LOCALE, new SetLocale());
		commands.put(CommandName.GO_TO_SUCCESS_PAGE, new GoToSuccessPage());
		commands.put(CommandName.GO_TO_NEWS_PAGE, new GoToNewsPage());
		commands.put(CommandName.GO_TO_PROFILE, new GoToProfile());
		commands.put(CommandName.LOG_OUT, new LogOut());
		commands.put(CommandName.UPDATE_USER, new UpdateUser());
		commands.put(CommandName.GO_TO_NEWS_REDACTOR, new GoToNewsRedactor());
		commands.put(CommandName.ADD_NEWS, new AddNews());
		commands.put(CommandName.UPDATE_NEWS, new UpdateNews());
		commands.put(CommandName.DELETE_NEWS, new DeleteNews());
		commands.put(CommandName.SEND_COMMENT, new SendComment());
		commands.put(CommandName.DELETE_COMMENT, new DeleteComment());
		commands.put(CommandName.PUT_NEWS_TO_FAVORITE, new PutNewsToFavorite());
		commands.put(CommandName.GO_TO_FAVORITE, new GoToFavorite());
		commands.put(CommandName.DELETE_FROM_FAVORITE, new DeleteNewsFromFavorite());
		commands.put(CommandName.GO_TO_USERS_NEWS, new GoToUsersNews());
		commands.put(CommandName.GO_TO_OFFERED_NEWS, new GoToOfferedNews());
	}
	
	public Command findCommand(String name) {
		if (name == null) {
			name = CommandName.UNKNOWN_COMMAND.toString();
		}		
		CommandName commandName;
		try {
		    commandName = CommandName.valueOf(name.toUpperCase());
		} catch(IllegalArgumentException e) {
			commandName = CommandName.UNKNOWN_COMMAND;	
		}
		Command command = commands.get(commandName);
		return command;
	}

}
