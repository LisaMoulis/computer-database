package persistence;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;
import persistence.DBConnection;

public class DBConnectionTest extends TestCase {

	public void testInstance() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field instance = DBConnection.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, null);
        DBConnection newInstance = DBConnection.getInstance();
        assertNotNull(newInstance);
	}
	
	public void testOpen() throws SQLException
	{
		Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
        connection.close();
	}
	
	public void testClose() throws SQLException
	{
        Connection connection = DBConnection.getConnection();
        assertNotNull(connection);
        DBConnection.close();
        assertTrue(connection.isClosed());
	}
}
