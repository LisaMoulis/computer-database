package ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorPages {

	@RequestMapping("/403")
	public ModelAndView page403(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ModelAndView page = new ModelAndView("error");
		page.addObject("message","Error 403: Access denied!");
		return page;
	}
	
	@RequestMapping("/404")
	public ModelAndView page404(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ModelAndView page = new ModelAndView("error");
		page.addObject("message","Error 404: Page not found. Too bad bitch!");
		return page;
	}
	
	@RequestMapping("/500")
	public ModelAndView page500(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ModelAndView page = new ModelAndView("error");
		page.addObject("message","Error 500: An error has occured!");
		return page;
	}
}
