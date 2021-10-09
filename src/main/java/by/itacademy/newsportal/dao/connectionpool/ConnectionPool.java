package by.itacademy.newsportal.dao.connectionpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {
	private final static ConnectionPool INSTANCE = new ConnectionPool();
	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;

	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private static int count = 0;

	private final static String SQL_EXCEPTION_IN_CON_POOL_ERROR = "SQLException in ConnectionPool";
	private final static String DRIVER_NOT_FOUND_ERROR = "Can't find database driver class";
	private final static String CONNECTING_TO_DATA_SOURCE_ERROR = "Error connection to the data source.";
	private final static String CLOSING_CONECTION_ERROR = "Error closing the connection.";
	private final static String RETURN_CONECTION_ERROR = "Connection isn't return to the pool.";
	private final static String CLOSE_RESULT_SET_ERROR = "ResultSet isn't closed.";
	private final static String CLOSE_STATEMENT_ERROR = "Statement isn't closed.";
	private final static String CONNECTION_IS_CLOSED_ERROR = "Attempting to close closed connection.";
	private final static String REMOVE_CONNECTION_FROM_GIVEN_AWAY_COLLECTION_ERROR = "Error deleting connecting from the given away connection pool.";
	private final static String ADD_CONNECTION_TO_CONNECTION_QUEUE_ERROR = "Error allocating connection in the pool.";

	public static ConnectionPool getInstance() throws ConnectionPoolException {				
		if (count == 0) {
			INSTANCE.initPoolData();
		}		
		return INSTANCE;
	}

	private ConnectionPool() {
		DBResourceManeger dbResourceManeger = DBResourceManeger.getInstance();
		this.driverName = dbResourceManeger.getValue(DBParametr.DB_DRIVER);
		this.url = dbResourceManeger.getValue(DBParametr.DB_URL);
		this.user = dbResourceManeger.getValue(DBParametr.DB_USER);
		this.password = dbResourceManeger.getValue(DBParametr.DB_PASSWORD);
		try {
			this.poolSize = Integer.parseInt(dbResourceManeger.getValue(DBParametr.DB_POOL_SIZE));
		} catch (NumberFormatException e) {
			poolSize = 5;
		}
	}
	
	private void initPoolData() throws ConnectionPoolException {
		count = 1;
		try {
			Class.forName(driverName);
			givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection);
				connectionQueue.add(pooledConnection);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException(SQL_EXCEPTION_IN_CON_POOL_ERROR, e);
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException(DRIVER_NOT_FOUND_ERROR, e);
		}
	}

	public void dispose() throws ConnectionPoolException {
		try {
			clearConnectionQueue();
		} catch (SQLException e) {
			throw new ConnectionPoolException(e);
		}
	}

	private void clearConnectionQueue() throws SQLException {
		try {
			closeConnectionQueue(givenAwayConQueue);
			closeConnectionQueue(connectionQueue);
		} catch (SQLException e) {
			throw new SQLException(CLOSING_CONECTION_ERROR, e);
		}
	}
	
	private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}	

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException(CONNECTING_TO_DATA_SOURCE_ERROR, e);
		}
		return connection;
	}	

	public void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) throws ConnectionPoolException {
		if (con == null || ps == null || rs == null) {
			throw new ConnectionPoolException(CONNECTING_TO_DATA_SOURCE_ERROR);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException(RETURN_CONECTION_ERROR, e);
		}

		try {
			rs.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException(CLOSE_RESULT_SET_ERROR, e);
		}

		try {
			ps.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException(CLOSE_STATEMENT_ERROR, e);
		}
	}

	public void closeConnection(Connection con, PreparedStatement ps) throws ConnectionPoolException {
		if (con == null || ps == null) {
			throw new ConnectionPoolException(CONNECTING_TO_DATA_SOURCE_ERROR);
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException(RETURN_CONECTION_ERROR, e);
		}

		try {
			ps.close();
		} catch (SQLException e) {
			throw new ConnectionPoolException(CLOSE_STATEMENT_ERROR, e);
		}
	}

	private class PooledConnection implements Connection {
		private Connection connection;

		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public void close() throws SQLException {
			if (connection.isClosed()) {
				throw new SQLException(CONNECTION_IS_CLOSED_ERROR);
			}

			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}

			if (!givenAwayConQueue.remove(this)) {
				throw new SQLException(REMOVE_CONNECTION_FROM_GIVEN_AWAY_COLLECTION_ERROR);
			}

			if (!connectionQueue.offer(this)) {
				throw new SQLException(ADD_CONNECTION_TO_CONNECTION_QUEUE_ERROR);
			}
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public java.sql.Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);

		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();

		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}
	}
}
