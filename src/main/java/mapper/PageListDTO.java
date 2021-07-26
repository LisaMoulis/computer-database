package mapper;

import java.io.Serializable;
import java.util.List;

import model.*;
import persistence.ComputerRequestHandler;

public class PageListDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1426831858291112030L;
	private int page;
	private int size;
	private List<Computer> computers;
	
	public PageListDTO()
	{
		this.page = 1;
		this.size = 10;
		this.computers = ComputerRequestHandler.getPage(size,(page-1)*size);
	}
	
	public PageListDTO(int page, int size)
	{
		this.page = page;
		this.size = size;
		this.size = 10;
		this.computers = ComputerRequestHandler.getPage(size,(page-1)*size);
	}
	
	public int getPage()
	{
		return this.page;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public int getNbPages()
	{
		int nbComputers = getNbComputers();
		if (nbComputers%size == 0)
		{
			return nbComputers/size;
		}
		return nbComputers/size + 1;
	}
	
	public List<Computer> getComputers()
	{
		return computers;
	}
	
	public int getNbComputers()
	{
		return ComputerRequestHandler.getNbComputers();
	}
	
	public void setPage(int page)
	{
		if (page < 1)
		{
			this.page = 1;
		}
		else if (page > getNbPages())
		{
			this.page = getNbPages();
		}
		else
		{
			this.page = page;
		}
		this.computers = ComputerRequestHandler.getPage(size,(this.page-1)*size);;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
}
