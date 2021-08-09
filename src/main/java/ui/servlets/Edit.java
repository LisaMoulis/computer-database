package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import builder.ComputerDTOBuilder;
import dto.ComputerDTO;
import mapper.ComputerDTOMapper;
import model.*;
import service.*;

@Controller
@RequestMapping("/edit")
public class Edit {

	private final Logger logger = LoggerFactory.getLogger(Edit.class);
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response,@RequestParam(name="id") int id) throws IOException, ServletException {
		logger.debug("Edit page displayed.");
		ComputerDTO c = computerDTOMapper.mapToDTO(computerService.getComputer(id));
		request.setAttribute("computer", c);
		if (c.getCompany() != null)
		{
			request.setAttribute("companyId",companyService.getCompany(c.getCompany()).getId());
		}
		else
		{
			request.setAttribute("companyId",0);
		}
		request.setAttribute("companies", companyService.getAllCompanies());
		logger.debug("The companies are "+ companyService.getAllCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/editComputer.jsp").forward(request, response);
		return "editComputer";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response, @RequestParam("computerName") String computerName, @RequestParam(name = "introduced", required = false) String introduced, @RequestParam(name = "discontinued", required = false) String discontinued,  @RequestParam(name = "companyId", required = false, defaultValue = "-1") int companyId) throws IOException, ServletException 
	{
		logger.debug("Computer info retrieved. Trying to create the computer.");
		ComputerDTOBuilder builder = new ComputerDTOBuilder();
		builder.setName(computerName);
		builder.setIntroduced(introduced);
		builder.setDiscontinued(discontinued);
		builder.setCompanyId(companyId);
		builder.setCompany(companyService.getCompany(companyId).getName());
		Computer computer = computerDTOMapper.mapToComputer(builder.build());
		computer.setId(Integer.valueOf(request.getParameter("id")));
		try
		{	
			computerService.updateComputer(computer);
			logger.debug("Computer updated. Redirection to the computer list.");
			return "redirect:/computers";
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer update failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.setAttribute("companies", companyService.getAllCompanies());
			RequestDispatcher rd= request.getRequestDispatcher("/add");
			rd.include(request, response);
			return "editComputer";
		}
	}
}
