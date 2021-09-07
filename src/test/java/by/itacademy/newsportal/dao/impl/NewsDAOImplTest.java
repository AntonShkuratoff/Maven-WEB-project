package by.itacademy.newsportal.dao.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import by.itacademy.newsportal.bean.News;
import by.itacademy.newsportal.bean.Role;
import by.itacademy.newsportal.dao.DAOException;
import by.itacademy.newsportal.dao.DAOProvider;
import by.itacademy.newsportal.dao.NewsDAO;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPool;
import by.itacademy.newsportal.dao.connectionpool.ConnectionPoolException;

class NewsDAOImplTest {
	private static final DAOProvider DAO_PROVIDER = DAOProvider.getDaoProvider();
	private static final NewsDAO NEWS_DAO = DAO_PROVIDER.getNEWS_DAO();
	
	private static ConnectionPool connectionPool = null;
	
	private final static int newsId = 29;
	private final static int numberOfEntries = 15;
	private final static String NEWS_TYPE = "published";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("@BeforeAll");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("@AfterAll");
		connectionPool = ConnectionPool.getInstance();		
		connectionPool.dispose();
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("@BeforeEach");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("@AfterEach");
	}
	
	@Test
	void testGetNumberOfEntries() throws DAOException, ConnectionPoolException {		
		int expected = numberOfEntries;
		int actual = NEWS_DAO.getNumberOfEntries();		
		assertEquals(expected, actual);
	}
	
	@Test	
	void testAddNews() throws DAOException {	
		int userId = 2;
		String title = "Test title";
		String brief = "Test brief";
		String content = "Test content";
		String role = Role.ADMIN.toString();
		News news = new News(userId, title, brief, content);
		assertTrue(NEWS_DAO.addNews(news, role));
	}	
	
	@Test
	void testGetNewsByIDIntString() throws DAOException {		
		String title = "Test title";
		String brief = "Test brief";
		String content = "Test content";		
		News newsActual = NEWS_DAO.getNewsByID(newsId, NEWS_TYPE);
		assertEquals(title, newsActual.getTitle());		
		assertEquals(brief, newsActual.getBrief());		
		assertEquals(content, newsActual.getContent());		
	}
	
	@Test
	void testDeleteNews() throws DAOException {
		assertTrue(NEWS_DAO.deleteNews(newsId, NEWS_TYPE, null));
	}
}
