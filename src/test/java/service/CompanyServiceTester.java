package service;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.zaxxer.hikari.HikariDataSource;

import model.Company;
import persistence.DBConnection;

public class CompanyServiceTester {

	private Connection connection =  Mockito.mock(Connection.class);
	private ResultSet result = Mockito.mock(ResultSet.class);
	private PreparedStatement query = Mockito.mock(PreparedStatement.class);
	private HikariDataSource dataSource = Mockito.mock(HikariDataSource.class);
	private HikariDataSource actualSource;
	
	@Before
	public void setMethods() throws NoSuchFieldException, SecurityException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		Mockito.when(result.getString("name")).thenReturn("testcompany");
		Mockito.when(result.getInt("id")).thenReturn(3);
		Mockito.when(result.next()).thenReturn(true).thenReturn(false);
		
		Mockito.when(query.executeQuery()).thenReturn(result);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(query);
		
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
		
		Field co = DBConnection.class.getDeclaredField("dataSource");
        co.setAccessible(true);
        actualSource = (HikariDataSource) co.get(co);
        co.set(co, dataSource);	
	}
	
	@After
	public void goBack() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field co = DBConnection.class.getDeclaredField("dataSource");
        co.setAccessible(true);
        co.set(co, actualSource);	
	}
	
	@Test
	public void testGetCompanyByName()
	{		
		Company c = CompanyService.getInstance().getCompany("testcompany");
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}	
	
	@Test
	public void testGetComputerById()
	{		
		Company c = CompanyService.getInstance().getCompany(3);
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}
}
