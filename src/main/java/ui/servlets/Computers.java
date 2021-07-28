package ui.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.PageListDTO;
import mapper.ComputerDTOMapper;
import model.ComputerList;
import service.PageService;

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
		if (request.getSession().getAttribute("page") == null)
		{
			page = new PageListDTO();
			logger.debug("Default settings applying.");
			request.getSession().setAttribute("page", page);
		}
		else
		{
			page = (PageListDTO) request.getSession().getAttribute("page");
		}
		
		if (request.getParameter("search") != null)
		{
			page.setSearch(request.getParameter("search"));
			request.setAttribute("search", page.getSearch());
		}
		
		if (request.getParameter("searchsubmit") != null && request.getParameter("searchsubmit") != "")
		{			
			if (request.getParameter("searchsubmit").equals("Filter by company"))
			{
				page.setOrder("company.name");
			}
			else if (request.getParameter("searchsubmit").equals("Filter by name"))
			{
				page.setOrder("computer.name");
			}
			else if (request.getParameter("searchsubmit").equals("Cancel"))
			{
				page.setSearch("");
				request.setAttribute("search", null);
			}
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
		ComputerList list = new ComputerList(page.getPage(),page.getSize());
		PageService.getInstance().getPage(list, page.getSearch(), page.getOrder());
		page.setComputers(ComputerDTOMapper.mapToDTOList(list.getComputers()));
		
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").include(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{		
		logger.debug("Settings changed.");
		this.doGet(request, response);
	}

}
