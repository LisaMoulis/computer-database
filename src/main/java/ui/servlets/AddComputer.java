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

public class AddComputer extends HttpServlet{
	private Displayer displayer = WebDisplayer.getInstance();
	
	
	@Override
	public String getServletInfo() {
		return "Add computer";
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/static/views/addComputer.html").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		ArrayList<String> args = new ArrayList<String>();
		args.add("create");
		args.add("name");
		args.add((String) request.getParameter("computerName"));
		if (request.getParameter("introduced") != null && !request.getParameter("introduced").equals(""))
		{
			args.add("introduced");
			args.add((String) request.getParameter("introduced"));
		}
		if (request.getParameter("discontinued") != null && !request.getParameter("discontinued").equals(""))
		{
			args.add("discontinued");
			args.add((String) request.getParameter("discontinued"));
		}
		if (request.getParameter("companyId") != null && !request.getParameter("companyId").equals("") && CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getParameter("companyId")))!= null)
		{
			args.add("company");
			args.add(CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getParameter("companyId"))));
		}
		CommandHandler.getInstance().exec(displayer,args.toArray(new String[args.size()]));
		//request.getRequestDispatcher("/static/views/dashboard.jsp").forward(request, response);
		response.sendRedirect("computers");
		//this.doGet(request, response);
	}

}
