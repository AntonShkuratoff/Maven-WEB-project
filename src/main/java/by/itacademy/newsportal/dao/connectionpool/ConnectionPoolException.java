package by.itacademy.newsportal.dao.connectionpool;

public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 1685054163305957794L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Exception e) {
		super(e);
	}

}
