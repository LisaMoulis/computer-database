package persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class DBConnection
 * Handle the connection
 */
public class DBConnection {

	private static DBConnection instance;
	private static Connection connection;
	
	
	private DBConnection()
	{
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/computer-database-db?" + "user=admincdb&password=qwerty1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	public static void close()
	{
		if (connection != null)
		{
			try {
				connection.close();
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
