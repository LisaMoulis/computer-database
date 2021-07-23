package ui.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mapper.PageListDTO;

public class Computers extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	@Override
	public String getServletInfo() {
		return "Computer list";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		PageListDTO page;
		if (request.getSession().getAttribute("page") == null || (request.getParameter("size") == null && request.getParameter("page") == null))
		{
			page = new PageListDTO();
			logger.debug("Default settings applying.");
			request.getSession().setAttribute("page", page);
		}
		else
		{
			page = (PageListDTO) request.getSession().getAttribute("page");
		}
		
		if (request.getParameter("size") != null)
		{
			page.setSize(Integer.valueOf(request.getParameter("size")));
			logger.debug("List size changed to the asked one.");
		}		
		request.setAttribute("page", page);
		if (request.getParameter("page") != null)
		{
			page.setPage(Integer.valueOf((String)request.getParameter("page")));
			logger.debug("Number of the page changed to the asked one.");
		}
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").include(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Settings changed.");
		this.doGet(request, response);
	}

}
