package mapper;

import dto.ComputerDTO;
import junit.framework.TestCase;
import model.Company;
import model.Computer;
import service.CompanyService;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.*;
import org.powermock.core.*;
import org.powermock.tests.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

public class ComputerDTOMapperTest extends TestCase {

	@PrepareForTest(CompanyService.class)
	
	@Test
	public void testToComputer() throws Exception
	{
		ComputerDTO dto = new ComputerDTO(1,"test","2021-01-01","2021-02-02","testcompany",3);
		
		CompanyService service =  Mockito.spy(CompanyService.getInstance());// = PowerMockito.mock(CompanyService.class);
		
		//PowerMockito.spy(CompanyService.class);
		
		//Mockito.when(CompanyService.class,"getInstance").thenReturn(service);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Computer c = ComputerDTOMapper.mapToComputer(dto);
		
		assertEquals("test",c.getName());
		assertEquals(LocalDate.of(2021, 1, 1),c.getIntroduced());
		assertEquals(LocalDate.of(2021, 2, 2),c.getDiscontinued());
		assertEquals("testcompany",c.getCompany());
	}
	
	public void testToDTO()
	{
		
	}
}
