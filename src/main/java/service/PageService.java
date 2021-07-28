package service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.*;
import persistence.ComputerRequestHandler;
import persistence.DBConnection;

public class PageService {

	private static final Logger logger =  LoggerFactory.getLogger(PageService.class);
	private static PageService instance;
	
	private PageService()
	{}
	
	public static PageService getInstance()
	{
		if (instance == null)
		{
			instance = new PageService();
		}
		return instance;
	}
	
	public List<Computer> getPage(ComputerList page, String search, String column)
	{
		page.setComputers(ComputerRequestHandler.getPage(page.getSize(),page.getOffset(),search,column));
		return page.getComputers();
	}
	
	public int getNbComputers(String search)
	{
		return ComputerRequestHandler.getNbComputers(search);
	}
	
}
