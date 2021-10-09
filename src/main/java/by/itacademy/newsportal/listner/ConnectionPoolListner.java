package by.itacademy.newsportal.listner;

import by.itacademy.newsportal.dao.connectionpool.ConnectionPool;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class ConnectionPoolListner
 *
 */
public class ConnectionPoolListner implements ServletContextListener {

	public ConnectionPoolListner() {}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			ConnectionPool.getInstance().dispose();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException(e);
		}
	}	
}
