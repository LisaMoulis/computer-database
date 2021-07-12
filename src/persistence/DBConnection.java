package persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static DBConnection instance;
	
	private DBConnection()
	{
		
	}
	
	public static DBConnection getInstance()
	{
		if (instance == null)
		{
			instance = new DBConnection();
		}
		return instance;
	}
	
	public static void open()
	{
		
	}
	
	public static void close()
	{
		
	}
	
}
