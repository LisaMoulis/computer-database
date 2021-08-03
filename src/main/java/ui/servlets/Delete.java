package ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.ComputerService;

@Controller
public class Delete extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(Delete.class);
	private WebApplicationContext springContext;
	@Autowired
	private ComputerService computerService;
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
	
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
		if (request.getParameter("selection") != null)
		{
			String[] selection = request.getParameter("selection").split(",");
			
			try
			{
				computerService.removeSelectedComputer(selection);
				logger.info("Computers deleted.");
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
