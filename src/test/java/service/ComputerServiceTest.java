package service;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.zaxxer.hikari.HikariDataSource;

import mapper.ComputerDAOMapper;
import model.Computer;
import persistence.DBConnection;

public class ComputerServiceTest {

	private Connection connection =  Mockito.mock(Connection.class);
	private ResultSet result = Mockito.mock(ResultSet.class);
	private PreparedStatement query = Mockito.mock(PreparedStatement.class);
	private HikariDataSource dataSource = Mockito.mock(HikariDataSource.class);
	private HikariDataSource actualSource;
	
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
	public void testGetComputerByName()
	{		
		Computer c = ComputerService.getInstance().getComputer("test");
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}	
	
	@Test
	public void testGetComputerById()
	{		
		Computer c = ComputerService.getInstance().getComputer(3);
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}
	
	@Test
	public void testDeleteComputerWithId() throws SQLException
	{
		ComputerService.getInstance().removeComputer(3);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(query).setInt(Mockito.eq(1),argument.capture());
		assertEquals(3,argument.getValue().intValue());	
	}
	
	@Test
	public void testDeleteComputerWithName() throws SQLException
	{
		ComputerService.getInstance().removeComputer("test");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		Mockito.verify(query).setString(Mockito.anyInt(),argument.capture());
		assertEquals("test",argument.getValue());	
	}
	
	@Test
	public void testDeleteAllComputers() throws SQLException
	{
		String [] args = {"3"};
		ComputerService.getInstance().removeSelectedComputer(args);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(query).setInt(Mockito.eq(1),argument.capture());
		assertEquals(3,argument.getValue().intValue());	
	}
	
	@Test
	public void testCreate() throws SQLException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		ComputerService.getInstance().createComputer(computer);
		Mockito.verify(connection).prepareStatement(argument.capture());
		
		assertEquals("INSERT INTO `computer`" + ComputerDAOMapper.mapToCreate(computer), argument.getValue());
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		ComputerService.getInstance().updateComputer(computer);
		Mockito.verify(connection).prepareStatement(argument.capture());
		
		assertEquals("UPDATE `computer` SET " + ComputerDAOMapper.mapToUpdate(computer) + "WHERE id=?", argument.getValue());
	}
}
