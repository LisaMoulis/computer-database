package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import dto.ComputerDTO;
import mapper.ComputerDTOMapper;
import model.*;
import service.*;

@Controller
@RequestMapping("/edit")
public class Edit {

	private final Logger logger = LoggerFactory.getLogger(Edit.class);
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(@RequestParam(name="id") int id) throws IOException, ServletException {
		ModelAndView page = new ModelAndView("editComputer");
		logger.debug("Edit page displayed.");
		ComputerDTO c = computerDTOMapper.mapToDTO(computerService.getComputer(id));
		page.addObject("computer", c);
		if (c.getCompany() != null && !c.getCompany().equals(""))
		{
			page.addObject("companyId",companyService.getCompany(c.getCompany()).getId());
		}
		else
		{
			page.addObject("companyId",0);
		}
		page.addObject("companies", companyService.getAllCompanies());
		logger.debug("The companies are "+ companyService.getAllCompanies());
		//request.getRequestDispatcher("/WEB-INF/static/views/editComputer.jsp").forward(request, response);
		return page;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response, @Valid ComputerDTO dto) throws IOException, ServletException 
	{
		logger.debug("Computer info retrieved. Trying to create the computer.");
		Computer computer = computerDTOMapper.mapToComputer(dto);
		try
		{	
			computerService.updateComputer(computer);
			logger.debug("Computer updated. Redirection to the computer list.");
			return "redirect:/computers";
		}
		catch (RuntimeException e)
		{
			response.setContentType( "text/html" );
			PrintWriter out = response.getWriter();
			logger.debug("Computer update failed. Error message : "+e.getMessage());
			out.println("<script>alert(\""+ e.getMessage()+"\")</script>");
			request.setAttribute("companies", companyService.getAllCompanies());
			RequestDispatcher rd= request.getRequestDispatcher("/add");
			rd.include(request, response);
			return "editComputer";
		}
	}
}
