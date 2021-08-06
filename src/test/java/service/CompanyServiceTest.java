package service;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.zaxxer.hikari.HikariDataSource;

import mapper.ComputerDAOMapper;
import model.Company;
import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;
import persistence.DBConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class CompanyServiceTest {
	
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
	}
	
	
	@Test
	public void testGetCompanyByName()
	{		
		DBConnection dbConnection = new DBConnection(dataSource);
		CompanyRequestHandler companyHandler = new CompanyRequestHandler(dbConnection);
		ComputerRequestHandler computerHandler = new ComputerRequestHandler(dbConnection,new ComputerDAOMapper());
		Company c = new CompanyService(computerHandler,companyHandler).getCompany("testcompany");
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}	
	
	@Test
	public void testGetCompanyById()
	{		
		DBConnection dbConnection = new DBConnection(dataSource);
		CompanyRequestHandler companyHandler = new CompanyRequestHandler(dbConnection);
		ComputerRequestHandler computerHandler = new ComputerRequestHandler(dbConnection,new ComputerDAOMapper());
		Company c = new CompanyService(computerHandler, companyHandler).getCompany(3);
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}
	
	@Test
	public void testDeleteCompany() throws SQLException
	{
		DBConnection dbConnection = new DBConnection(dataSource);
		CompanyRequestHandler companyHandler = new CompanyRequestHandler(dbConnection);
		ComputerRequestHandler computerHandler = new ComputerRequestHandler(dbConnection,new ComputerDAOMapper());
		new CompanyService(computerHandler,companyHandler).removeCompany("testcompany");
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(query).setInt(Mockito.anyInt(),argument.capture());
		assertEquals(3,argument.getValue().intValue());	
	}
}