package ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.ComputerList;
import service.displayer.Displayer;
import service.displayer.WebDisplayer;

public class Computers extends HttpServlet{
	private Displayer displayer = WebDisplayer.getInstance();
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	@Override
	public String getServletInfo() {
		return "Computer list";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int current = 1;
		int size = 10;
		logger.debug("Default settings applying.");
		if (request.getParameter("size") != null)
		{
			size = Integer.valueOf(request.getParameter("size"));
			logger.debug("List size changed to the asked one.");
		}
		int nbPages = ComputerList.getInstance().getNbPages(size);
		
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("nbComputers", ComputerList.getInstance().getNbComputers());
		request.setAttribute("size", size);
		if (request.getParameter("page") != null)
		{
			current = Integer.valueOf((String)request.getParameter("page"));
			logger.debug("Number of the page changed to the asked one.");
		}
		if (current < 1)
		{
			logger.debug("Page number inferior to 1, going to page 1.");
			current = 1;
		}
		else if (current > nbPages)
		{
			logger.debug("Page number superior to the actual number of pages, going to the last page.");
			current = nbPages;
		}
		request.setAttribute("current",current);
		request.setAttribute("computers", ComputerList.getInstance().getPage(current,size));
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Settings changed.");
		this.doGet(request, response);
	}

}
