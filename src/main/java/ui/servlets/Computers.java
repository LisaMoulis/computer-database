package ui.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dto.PageListDTO;
import mapper.ComputerDTOMapper;
import model.ComputerList;
import service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.*;
import org.springframework.beans.factory.config.*;

@Controller
@WebServlet("/computers")
public class Computers extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(AddComputer.class);
	private WebApplicationContext springContext;
	
	private PageService pageService;
	
	private ComputerDTOMapper computerDTOMapper;
	
	@Autowired
	public void setPageService(PageService pageService)
	{
		this.pageService = pageService;
	}
	
	@Autowired
	public void setComputerDTOMapper(ComputerDTOMapper computerDTOMapper)
	{
		this.computerDTOMapper = computerDTOMapper;
	}
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
	
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
		
		if (request.getParameter("searchsubmit") != null && !request.getParameter("searchsubmit").equals(""))
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
		
		if (request.getParameter("searchorder") != null && !request.getParameter("searchorder").equals(""))
		{
			if (request.getParameter("searchorder").equals("Descending"))
			{
				page.setSense("desc");
			}
			else
			{
				page.setSense("asc");
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
		page.setNbComputers(pageService.getNbComputers(page.getSearch()));
		ComputerList list = new ComputerList(page.getPage(),page.getSize());
		pageService.getPage(list, page.getSearch(), page.getOrder(),page.getSense());
		page.setComputers(computerDTOMapper.mapToDTOList(list.getComputers()));
		
		request.getRequestDispatcher("/WEB-INF/static/views/dashboard.jsp").include(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
	{		
		logger.debug("Settings changed.");
		this.doGet(request, response);
	}

}
