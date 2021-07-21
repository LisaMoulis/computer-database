package ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.ComputerList;
import service.displayer.Displayer;
import service.displayer.WebDisplayer;

public class Computers extends HttpServlet{
	private Displayer displayer = WebDisplayer.getInstance();
	
	
	@Override
	public String getServletInfo() {
		return "My home page";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int current = 1;
		int size = 10;

		if (request.getParameter("size") != null)
		{
			size = Integer.valueOf(request.getParameter("size"));
		}
		int nbPages = ComputerList.getInstance().getNbPages(size);
		
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("nbComputers", ComputerList.getInstance().getNbComputers());
		request.setAttribute("size", size);
		if (request.getParameter("page") != null)
		{
			current = Integer.valueOf((String)request.getParameter("page"));
		}
		if (current < 1)
		{
			current = 1;
		}
		else if (current > nbPages)
		{
			current = nbPages;
		}
		request.setAttribute("current",current);
		request.setAttribute("computers", ComputerList.getInstance().getPage(current,size));
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		this.doGet(request, response);
	}

}
