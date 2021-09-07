package by.itacademy.newsportal.dao.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManeger {
	private final static DBResourceManeger INSTANCE = new DBResourceManeger();
	
	private ResourceBundle bundle = ResourceBundle.getBundle("resources.properties.db");	
	
	public static DBResourceManeger getInstance() {
		return INSTANCE;
	}
	
	public String getValue(String key) {
		return bundle.getString(key);
	}
}
