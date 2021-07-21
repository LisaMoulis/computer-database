package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import command.CommandHandler;
import model.CompanyList;
import service.displayer.Displayer;
import service.displayer.WebDisplayer;
import mapper.*;

public class AddComputer extends HttpServlet{
	private Displayer displayer = WebDisplayer.getInstance();
	
	@Override
	public String getServletInfo() {
		return "Add computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/static/views/addComputer.html").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		try
		{
			ArrayList<String> args = ComputerMapper.mapToCreateArgs(request);
			CommandHandler.getInstance().exec(displayer,args.toArray(new String[args.size()]));
			response.sendRedirect("computers");
			
			//request.getRequestDispatcher("/static/views/dashboard.jsp").forward(request, response);
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.getRequestDispatcher("/WEB-INF/static/views/addComputer.html").include(request, response);
		}
	}

}
