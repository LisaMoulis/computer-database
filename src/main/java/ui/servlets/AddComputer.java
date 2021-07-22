package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import command.Command;
import command.CommandHandler;
import model.CompanyList;
import model.ComputerList;
import service.displayer.Displayer;
import service.displayer.WebDisplayer;
import mapper.*;

public class AddComputer extends HttpServlet{
	private Displayer displayer = WebDisplayer.getInstance();
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	
	@Override
	public String getServletInfo() {
		return "Add computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Add page displayed.");
		request.setAttribute("companies", CompanyList.getInstance().getCompanies());
		logger.debug("The companies are "+ CompanyList.getInstance().getCompanies());
		request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		logger.debug("Computer info retreived. Trying to create the computer.");
		try
		{
			ArrayList<String> args = ComputerMapper.mapToCreateArgs(request);
			CommandHandler.getInstance().exec(displayer,args.toArray(new String[args.size()]));
			logger.debug("Computer created with command '"+ args +"'. Redirection to the computer list.");
			response.sendRedirect("computers");
			
			//request.getRequestDispatcher("/static/views/dashboard.jsp").forward(request, response);
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer creation failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.setAttribute("companies", CompanyList.getInstance().getCompanies());
			request.getRequestDispatcher("/WEB-INF/static/views/addComputer.jsp").include(request, response);
		}
	}

}
