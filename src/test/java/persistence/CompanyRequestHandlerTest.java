package persistence;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.zaxxer.hikari.HikariDataSource;

import model.Company;

public class CompanyRequestHandlerTest {

	private CompanyRequestHandler companyRequestHandler;
	
	private Connection connection =  Mockito.mock(Connection.class);
	private ResultSet result = Mockito.mock(ResultSet.class);
	private PreparedStatement query = Mockito.mock(PreparedStatement.class);
	private HikariDataSource dataSource = Mockito.mock(HikariDataSource.class);
	
	@Before
	public void setMethods() throws NoSuchFieldException, SecurityException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		Mockito.when(result.getString("name")).thenReturn("testcompany");
		Mockito.when(result.getInt("id")).thenReturn(3);
		Mockito.when(result.next()).thenReturn(true).thenReturn(false);
		
		Mockito.when(query.executeQuery()).thenReturn(result);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(query);
		
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
		DBConnection dbConnection = new DBConnection(dataSource);
		companyRequestHandler = new CompanyRequestHandler(dbConnection);
		
	}

	@Test
	public void testGetCompanyByName()
	{		
		Company c = companyRequestHandler.getCompany("testcompany");
		assertEquals(3,c.getId());
		assertEquals("testcompany",c.getName());
	}	
	
	@Test
	public void testGetCompanyById()
	{		
		Company c = companyRequestHandler.getCompany(3);
		assertEquals(3,c.getId());
		assertEquals("testcompany",c.getName());
	}
	
	@Test
	public void testAllCompanies()
	{		
		ArrayList<Company> alls = companyRequestHandler.getAllCompanies();
		Company c = alls.get(0);
		assertEquals(3,c.getId());
		assertEquals("testcompany",c.getName());
	}
	
	
	
}
