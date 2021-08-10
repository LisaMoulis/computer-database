package ui.servlets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import mapper.ComputerDTOMapper;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

public class AddComputerTest {

	private AddComputer servlet = new AddComputer(); 
	@Autowired
	private CompanyService companyService;
	private ComputerService computerService = Mockito.mock(ComputerService.class);

	
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	
	@Before
	public void setInjections() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		servlet.setCompanyService(companyService);
		servlet.setComputerService(computerService);
        servlet.setComputerDTOMapper(new ComputerDTOMapper(companyService));
	}
	
	@Test
	public void testEmpty() throws IOException, ServletException
	{
		HttpServletRequest request = mock(HttpServletRequest.class);       
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    
	    Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
		Mockito.when(request.getParameter("computerName")).thenReturn("test");
		
	    servlet.doPost(request, response);
	    ArgumentCaptor<Computer> c = ArgumentCaptor.forClass(Computer.class);
		Mockito.verify(computerService).createComputer(c.capture());
		assertEquals("test",c.getValue().getName());
		assertNull(c.getValue().getIntroduced());
		assertNull(c.getValue().getDiscontinued());
		assertEquals("",c.getValue().getCompany());
	}
}
