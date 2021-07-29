package dto;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.mockito.Mockito;

import junit.framework.TestCase;
import service.CompanyService;

public class ComputerDTOTest extends TestCase {

	@Mock CompanyService companyList = CompanyService.getInstance();
	
	
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
	
	public void testFullRequest()
	{
		HttpServletRequest request = mock(HttpServletRequest.class); 
		Mockito.when(request.getParameter("computerName")).thenReturn("test");
		Mockito.when(request.getParameter("introduced")).thenReturn("2021-01-01");
		Mockito.when(request.getParameter("discontinued")).thenReturn("2021-02-02");
		Mockito.when(request.getParameter("companyId")).thenReturn("3");
		ComputerDTO dto = new ComputerDTO(request);
		assertEquals("test",dto.getName());
		assertEquals("2021-01-01",dto.getIntroduced());
		assertEquals("2021-02-02",dto.getDiscontinued());
		assertEquals(companyList.getCompany(3).getName(),dto.getCompany());
		assertEquals(3,dto.getCompanyId());
	}
}
