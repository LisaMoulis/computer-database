package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import model.*;
import service.CompanyService;
import service.ComputerService;
import mapper.*;

public class AddComputer extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	@Override
	public String getServletInfo() {
		return "Add computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Add page displayed.");
		CompanyList companies = new CompanyList();
		companies.setCompanies(CompanyService.getInstance().getAllCompanies());
		request.setAttribute("companies", companies.getCompanies());
		logger.debug("The companies are "+ companies.getCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retrieved. Trying to create the computer.");
		Computer computer = ComputerDTOMapper.mapToComputer(new ComputerDTO(request));
		try
		{
			ComputerService.getInstance().createComputer(computer);
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
			companies.setCompanies(CompanyService.getInstance().getAllCompanies());
			request.setAttribute("companies", companies.getCompanies());
			request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").include(request, response);
		}
	}

}
