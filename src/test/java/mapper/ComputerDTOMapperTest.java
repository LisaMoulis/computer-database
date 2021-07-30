package mapper;

import dto.ComputerDTO;
import junit.framework.TestCase;
import model.Company;
import model.Computer;
import service.CompanyService;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.Test;
import org.mockito.Mockito;

public class ComputerDTOMapperTest extends TestCase {

	
	@Test
	public void testToComputer() throws Exception
	{
		ComputerDTO dto = new ComputerDTO(1,"test","2021-01-01","2021-02-02","testcompany",3);
		
		Computer c = ComputerDTOMapper.mapToComputer(dto);
		
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}
	
	public void testToDTO() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Computer c = new Computer(1,"test",LocalDate.of(2021,1,1),LocalDate.of(2021,2,2),"testcompany");
		
		CompanyService service =  Mockito.mock(CompanyService.class);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Mockito.when(service.getCompany("testcompany")).thenReturn(new Company(3,"testcompany"));
		
		Field instance = CompanyService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, service);
		
		ComputerDTO dto = ComputerDTOMapper.mapToDTO(c);
		
		assertEquals("test",dto.getName());
		assertEquals("2021-01-01",dto.getIntroduced());
		assertEquals("2021-02-02",dto.getDiscontinued());
		assertEquals("testcompany",dto.getCompany());
	}
}
