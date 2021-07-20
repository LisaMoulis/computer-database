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
		/*ArrayList<String> args = new ArrayList<String>();
		args.add("create");
		args.add("name");
		args.add((String) request.getAttribute("computerName"));
		if (request.getAttribute("introduced") != null)
		{
			args.add("introduced");
			args.add((String) request.getAttribute("introduced"));
		}
		if (request.getAttribute("discontinued") != null)
		{
			args.add("discontinued");
			args.add((String) request.getAttribute("discontinued"));
		}
		if (request.getAttribute("companyId") != null && CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getAttribute("companyId")))!= null)
		{
			args.add("company");
			args.add(CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getAttribute("companyId"))));
		}
		CommandHandler.getInstance().exec(displayer,(String[]) args.toArray());
		request.getRequestDispatcher("/static/views/dashboard.jsp").forward(request, response);*/
		this.doGet(request, response);
	}

}
