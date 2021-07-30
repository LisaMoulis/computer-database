package dto;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mockito;

import junit.framework.TestCase;
import model.Company;
import service.CompanyService;

public class ComputerDTOTest extends TestCase {
	
	public void testEmptyRequest()
	{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class); 
		ComputerDTO dto = new ComputerDTO(request);
		assertNull(dto.getName());
		assertNull(dto.getIntroduced());
		assertNull(dto.getDiscontinued());
		assertNull(dto.getCompany());
		assertEquals(-1,dto.getId());
		assertEquals(-1,dto.getCompanyId());
	}
	
	public void testFullRequest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		HttpServletRequest request = mock(HttpServletRequest.class); 
		Mockito.when(request.getParameter("computerName")).thenReturn("test");
		Mockito.when(request.getParameter("introduced")).thenReturn("2021-01-01");
		Mockito.when(request.getParameter("discontinued")).thenReturn("2021-02-02");
		Mockito.when(request.getParameter("companyId")).thenReturn("3");
		
		CompanyService service =  Mockito.mock(CompanyService.class);
		Mockito.when(service.getCompany(3)).thenReturn(new Company(3,"testcompany"));
		Mockito.when(service.getCompany("testcompany")).thenReturn(new Company(3,"testcompany"));
		
		Field instance = CompanyService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, service);
		ComputerDTO dto = new ComputerDTO(request);
		assertEquals("test",dto.getName());
		assertEquals("2021-01-01",dto.getIntroduced());
		assertEquals("2021-02-02",dto.getDiscontinued());
		assertEquals("testcompany",dto.getCompany());
		assertEquals(3,dto.getCompanyId());
	}
}
