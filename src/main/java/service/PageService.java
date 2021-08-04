package service;

import java.util.List;

import model.*;
import persistence.ComputerRequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

@Component("pagerService")
@Scope("singleton")
public class PageService {

	//private static PageService instance;
	@Autowired
	private ComputerRequestHandler computerRequestHandler;

	public PageService()
	{}
	
	/*public static PageService getInstance()
	{
		if (instance == null)
		{
			instance = new PageService();
		}
		return instance;
	}*/
	
	public List<Computer> getPage(ComputerList page, String search, String column)
	{
		page.setComputers(computerRequestHandler.getPage(page.getSize(),page.getOffset(),search,column));
		return page.getComputers();
	}
	
	public int getNbComputers(String search)
	{
		return computerRequestHandler.getNbComputers(search);
	}
	
}
