package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import builder.ComputerDTOBuilder;
import model.*;
import service.CompanyService;
import service.ComputerService;
import mapper.*;

@Controller
public class AddComputer extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	private WebApplicationContext springContext;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	
	@Override
	public String getServletInfo() {
		return "Add computer";
	}
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Add page displayed.");
		CompanyList companies = new CompanyList();
		companies.setCompanies(companyService.getAllCompanies());
		request.setAttribute("companies", companies.getCompanies());
		logger.debug("The companies are "+ companies.getCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retrieved. Trying to create the computer.");
		ComputerDTOBuilder builder = new ComputerDTOBuilder();
		builder.setName((String) request.getParameter("computerName"));
		builder.setIntroduced((String) request.getParameter("introduced"));
		builder.setDiscontinued( (String) request.getParameter("discontinued"));
		if (request.getParameter("companyId") != null && !request.getParameter("companyId").equals("") && companyService.getCompany(Integer.parseInt((String) request.getParameter("companyId")))!= null)
		{
			int companyId = Integer.parseInt((String) request.getParameter("companyId"));
			builder.setCompanyId(companyId);
			builder.setCompany(companyService.getCompany(companyId).getName());
		}
		
		Computer computer = computerDTOMapper.mapToComputer(builder.build());
		try
		{
			computerService.createComputer(computer);
			logger.debug("Computer created. Redirection to the computer list.");
			response.sendRedirect("computers");
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
			request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").include(request, response);
		}
	}

}
