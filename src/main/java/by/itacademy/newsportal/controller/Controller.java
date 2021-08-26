package by.itacademy.newsportal.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String COMMAND_PARAM_REQUEST_NAME = "command";
	
	private final CommandProvider PROVIDER = new CommandProvider();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandName = request.getParameter(COMMAND_PARAM_REQUEST_NAME );
		Command command = PROVIDER.findCommand(commandName);
		command.execute(request, response);
		
	}
	
}
