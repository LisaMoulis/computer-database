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
import dto.ComputerDTO;
import mapper.ComputerDTOMapper;
import model.*;
import service.*;

@Controller
public class Edit extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(Edit.class);
	private WebApplicationContext springContext;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
	
	@Override
	public String getServletInfo() {
		return "Edit computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Edit page displayed.");
		int id = Integer.parseInt(request.getParameter("id"));
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
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retrieved. Trying to update the computer.");
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
		computer.setId(Integer.valueOf(request.getParameter("id")));
		try
		{	
			computerService.updateComputer(computer);
			logger.debug("Computer updated. Redirection to the computer list.");
			response.sendRedirect("computers");
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer update failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.setAttribute("companies", companyService.getAllCompanies());
			request.getRequestDispatcher("/WEB-INF/static/views/editComputer.jsp").include(request, response);
		}
	}
}
