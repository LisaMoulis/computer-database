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
import model.*;
import service.CompanyService;
import service.ComputerService;
import mapper.*;

@Controller
@RequestMapping("/add")
public class AddComputer {

	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);

	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerDTOMapper computerDTOMapper;
	
	@Autowired
	public void setCompanyService(CompanyService companyService)
	{
		this.companyService = companyService;
	}
	
	@Autowired
	public void setComputerService(ComputerService computerService)
	{
		this.computerService = computerService;
	}
	
	@Autowired
	public void setComputerDTOMapper(ComputerDTOMapper computerDTOMapper)
	{
		this.computerDTOMapper = computerDTOMapper;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Add page displayed.");
		CompanyList companies = new CompanyList();
		companies.setCompanies(companyService.getAllCompanies());
		request.setAttribute("companies", companies.getCompanies());
		logger.debug("The companies are "+ companies.getCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").forward(request, response);
		return "addComputer";
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
		try
		{
			computerService.createComputer(computer);
			logger.debug("Computer created. Redirection to the computer list.");
			return "redirect:/computers";
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer creation failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			CompanyList companies = new CompanyList();
			companies.setCompanies(companyService.getAllCompanies());
			request.setAttribute("companies", companies.getCompanies());
			RequestDispatcher rd= request.getRequestDispatcher("/add");
			rd.include(request, response);
			return "addComputer";
		}
	}

}
