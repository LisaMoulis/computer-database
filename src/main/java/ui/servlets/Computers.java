package ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;


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
		/*PrintWriter out = response.getWriter();
		out.println( "<html>" );
		out.println( "<head>");
		out.println( "<title>Computers</title>" );
		out.println( "</head>" );
		out.println( "<body>" );
		out.println( "<h1>Home</h1><p>Home, sweet home</p>" );
		out.println( "</body>" );
		out.println( "</html>" );
		out.close();*/
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{
		
		this.doGet(request, response);
	}

}
