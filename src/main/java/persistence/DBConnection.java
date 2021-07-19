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
	}
	
	/**
	 * @return instance	The unique instance of the class
	 */
	public static DBConnection getInstance()
	{
		//Create the instance if it's not existing
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

	/**
	 * Close the connection to the database
	 */
	public static void close()
	{
		//Close the connection if it's existing
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
	
	/**
	 * @return The connection to the database
	 */
	public static Connection getConnection()
	{
		//Create the instance if it's not existing
		if (connection == null)
		{
			if (instance == null)
			{
				instance = new DBConnection();
			}
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
		return connection;
	}
	
	@Override
	public void finalize()
	{
		//Close the connection as soon as the instance is available to the garbage collector taking
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
