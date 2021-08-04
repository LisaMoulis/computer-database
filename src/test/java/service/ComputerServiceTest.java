package service;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.zaxxer.hikari.HikariDataSource;

import mapper.ComputerDAOMapper;
import model.Computer;
import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;
import persistence.DBConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mockContext.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ComputerServiceTest {

	
	private Connection connection =  Mockito.mock(Connection.class);
	private ResultSet result = Mockito.mock(ResultSet.class);
	private PreparedStatement query = Mockito.mock(PreparedStatement.class);
	private HikariDataSource dataSource = Mockito.mock(HikariDataSource.class);
	
	@Before
	public void setMethods() throws NoSuchFieldException, SecurityException, SQLException, IllegalArgumentException, IllegalAccessException
	{
		Mockito.when(result.getString("name")).thenReturn("test");
		Mockito.when(result.getBytes("introduced")).thenReturn(new String("2021-01-01").getBytes());
		Mockito.when(result.getString("introduced")).thenReturn("2021-01-01 00:00:00");
		Mockito.when(result.getTimestamp("introduced")).thenReturn(Timestamp.valueOf("2021-01-01 00:00:00"));
		Mockito.when(result.getBytes("discontinued")).thenReturn(new String("2021-02-02").getBytes());
		Mockito.when(result.getString("discontinued")).thenReturn("2021-02-02 00:00:00");
		Mockito.when(result.getTimestamp("discontinued")).thenReturn(Timestamp.valueOf("2021-02-02 00:00:00"));
		Mockito.when(result.getString("company.name")).thenReturn("testcompany");
		Mockito.when(result.getInt("computer.id")).thenReturn(3);
		Mockito.when(result.next()).thenReturn(true).thenReturn(false);
		
		Mockito.when(query.executeQuery()).thenReturn(result);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(query);
		
		Mockito.when(dataSource.getConnection()).thenReturn(connection);
	}
	
	/*@Before
	public void getCoAndQuery() throws SQLException
	{
		connection = dbConnection.getConnection();
		query = connection.prepareStatement("FROM `computer`");
	}*/
	
	@Test
	public void testGetComputerByName()
	{
		DBConnection dbConnection = new DBConnection(dataSource);
		CompanyRequestHandler companyHandler = new CompanyRequestHandler(dbConnection);
		ComputerRequestHandler computerHandler = new ComputerRequestHandler(dbConnection,new ComputerDAOMapper(new CompanyService()));
		Computer c = new ComputerService(new ComputerRequestHandler(dbConnection,new ComputerDAOMapper(new CompanyService()))).getComputer("test");
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}	
	
	@Test
	public void testGetComputerById()
	{		
		Computer c = computerService.getComputer(3);
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}
	
	@Test
	public void testDeleteComputerWithId() throws SQLException
	{
		Connection connection = dbConnection.getConnection();
		PreparedStatement query = connection.prepareStatement("FROM `computer`");
		computerService.removeComputer(3);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(query).setInt(Mockito.eq(1),argument.capture());
		assertEquals(3,argument.getValue().intValue());	
	}
	
	@Test
	public void testDeleteComputerWithName() throws SQLException
	{
		Connection connection = dbConnection.getConnection();
		PreparedStatement query = connection.prepareStatement("FROM `computer`");
		computerService.removeComputer("test");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		Mockito.verify(query).setString(Mockito.anyInt(),argument.capture());
		assertEquals("test",argument.getValue());	
	}
	
	@Test
	public void testDeleteAllComputers() throws SQLException
	{
		String [] args = {"3"};
		Connection connection = dbConnection.getConnection();
		PreparedStatement query = connection.prepareStatement("FROM `computer`");
		computerService.removeSelectedComputer(args);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(query,Mockito.atLeastOnce()).setInt(Mockito.eq(1),argument.capture());
		//List<String> values = argument.getAllValues()
		assertEquals(3,argument.getValue().intValue());	
	}
	
	@Test
	public void testCreate() throws SQLException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

		Connection connection = dbConnection.getConnection();
		Mockito.verify(connection,Mockito.atLeastOnce()).prepareStatement(argument.capture());
		computerService.createComputer(computer);
		Mockito.verify(connection,Mockito.atLeastOnce()).prepareStatement(argument.capture());
		List<String> values = argument.getAllValues();
		//System.out.println(values);
		assertEquals(values.toString(),"INSERT INTO `computer`" + computerDAOMapper.mapToCreate(computer), argument.getValue());
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		Connection connection = dbConnection.getConnection();
		computerService.updateComputer(computer);
		Mockito.verify(connection,Mockito.atLeastOnce()).prepareStatement(argument.capture());
		
		assertEquals("UPDATE `computer` SET " + computerDAOMapper.mapToUpdate(computer) + "WHERE id=?", argument.getValue());
	}
}
