package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.ComputerDTO;
import mapper.ComputerDTOMapper;
import model.CompanyList;
import model.Computer;
import service.ComputerService;

public class Edit extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(Edit.class);
	
	@Override
	public String getServletInfo() {
		return "Edit computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Edit page displayed.");
		int id = Integer.parseInt(request.getParameter("id"));
		Computer c = ComputerService.getInstance().getComputer(id);
		request.setAttribute("computer", c);
		if (c.getCompany() != null)
		{
			request.setAttribute("companyId",CompanyList.getInstance().getCompany(c.getCompany()).getId());
		}
		else
		{
			request.setAttribute("companyId",0);
		}
		request.setAttribute("companies", CompanyList.getInstance().getCompanies());
		logger.debug("The companies are "+ CompanyList.getInstance().getCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/editComputer.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retrieved. Trying to update the computer.");
		Computer computer = ComputerDTOMapper.mapToComputer(new ComputerDTO(request));
		computer.setId(Integer.valueOf(request.getParameter("id")));
		try
		{	
			ComputerService.getInstance().updateComputer(computer);
			logger.debug("Computer updated. Redirection to the computer list.");
			response.sendRedirect("computers");
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer update failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.setAttribute("companies", CompanyList.getInstance().getCompanies());
			request.getRequestDispatcher("/WEB-INF/static/views/editComputer.jsp").include(request, response);
		}
	}
}
