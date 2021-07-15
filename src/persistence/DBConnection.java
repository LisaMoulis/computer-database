package persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class DBConnection :
 * Handle the connection
 * @author Lisa
 */
public class DBConnection {

	private static DBConnection instance;
	private static Connection connection;
	private static final Logger logger =  LoggerFactory.getLogger(DBConnection.class);
	
	private DBConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			connection = DriverManager.getConnection("jdbc:mysql://localhost/computer-database-db?" + "user=admincdb&password=qwerty1234");
			logger.debug("Connection to the database etablished.");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug("Failed to establish a connection to the database.");
			e.printStackTrace();
		}

	}
	
	public static DBConnection getInstance()
	{
		if (instance == null)
		{
			instance = new DBConnection();
		}
		return instance;
	}
	
	public static Logger getLogger()
	{
		return logger;
	}

	public static void close()
	{
		if (connection != null)
		{
			try {
				connection.close();
				logger.debug("Connection closed.");
			} catch (SQLException e) {
				System.out.println("Impossible to close the connection. ");
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection()
	{
		if (connection == null || instance == null)
		{
			instance = new DBConnection();
		}
		return connection;
	}
	
	@Override
	public void finalize()
	{
		try {
			if (!connection.isClosed())
			{
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
