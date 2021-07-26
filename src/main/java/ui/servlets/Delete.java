package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mapper.ComputerDTO;
import mapper.ComputerDTOMapper;
import model.CompanyList;
import model.Computer;
import model.ComputerList;
import persistence.ComputerRequestHandler;
import service.Validator;

public class Delete extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(Delete.class);
	
	@Override
	public String getServletInfo() {
		return "Edit computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Delete page loaded.");
		response.sendRedirect("computers");
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retrieved. Trying to update the computer.");
		System.out.println("coucou");
		System.out.println(request.getParameter("selection"));
		if (request.getParameter("selection") != null)
		{
			String[] selection = request.getParameter("selection").split(",");
			
			try
			{
				for (String nb : selection)
				{
					ComputerRequestHandler.deleteComputer(Integer.valueOf(nb));
					logger.info("Computers deleted.");
				}
			}
			catch (RuntimeException e)
			{
				PrintWriter out = response.getWriter();
				logger.debug("A computer deletion failed. Error message : "+e.getMessage());
				out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			}
		}
		response.sendRedirect("computers");
	}
}
