package mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mockito;

import junit.framework.TestCase;
import model.Company;
import model.Computer;
import service.CompanyService;

public class ComputerDAOMapperTest extends TestCase {
	
	public void testToComputer() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, SQLException
	{
		ResultSet result = Mockito.mock(ResultSet.class);
		CompanyService service =  Mockito.mock(CompanyService.class);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Mockito.when(service.getCompany("testcompany")).thenReturn(new Company(3,"testcompany"));
		
		Field instance = CompanyService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, service);
		Computer c;
		Mockito.when(result.getString("name")).thenReturn("test");
		Mockito.when(result.getBytes("introduced")).thenReturn(new String("2021-01-01").getBytes());
		Mockito.when(result.getString("introduced")).thenReturn("2021-01-01 00:00:00");
		Mockito.when(result.getTimestamp("introduced")).thenReturn(Timestamp.valueOf("2021-01-01 00:00:00"));
		Mockito.when(result.getBytes("discontinued")).thenReturn(new String("2021-02-02").getBytes());
		Mockito.when(result.getString("discontinued")).thenReturn("2021-02-02 00:00:00");
		Mockito.when(result.getTimestamp("discontinued")).thenReturn(Timestamp.valueOf("2021-02-02 00:00:00"));
		Mockito.when(result.getString("company.name")).thenReturn("testcompany");
		Mockito.when(result.getInt("computer.id")).thenReturn(3);
		c = ComputerDAOMapper.mapToComputer(result);
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
		
	}
	
	@Test
	public void testToUpdate() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		CompanyService service =  Mockito.mock(CompanyService.class);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Mockito.when(service.getCompany("testcompany")).thenReturn(new Company(3,"testcompany"));
		
		Field instance = CompanyService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, service);
		String request = ComputerDAOMapper.mapToUpdate(computer);
		assertEquals("`id`='1', `name`='test', `introduced`='2021-01-01 00:00:00.0', `discontinued`='2021-02-02 00:00:00.0', `company_id`='3'",request);

	}
	
	public void testToCreate() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Computer computer = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		CompanyService service =  Mockito.mock(CompanyService.class);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Mockito.when(service.getCompany("testcompany")).thenReturn(new Company(3,"testcompany"));
		
		Field instance = CompanyService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, service);
		String request = ComputerDAOMapper.mapToCreate(computer);
		assertEquals("(`name`, `introduced`, `discontinued`, `company_id`) VALUES ('test', '2021-01-01 00:00:00.0', '2021-02-02 00:00:00.0', 3)",request);

	}
}
