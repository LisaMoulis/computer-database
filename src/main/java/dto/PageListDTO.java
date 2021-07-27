package dto;

import java.util.List;

import model.Computer;
import persistence.ComputerRequestHandler;

public class PageListDTO {
	
	private int page = 1;
	private int size = 10;
	private String search = "";
	private String order = "computer.name";
	
	protected List<Computer> computers;
	
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
		this.computers = ComputerRequestHandler.getPage(size,(this.page-1)*size,this.search,order);
		return computers;
	}
	
	public int getNbComputers()
	{
		return ComputerRequestHandler.getNbComputers(this.search);
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
	}
	
	public void setOrder(String o)
	{
		this.order = o;
	}
	
	public void setSearch(String s)
	{
		this.search = s;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}

	public String getSearch() {
		// TODO Auto-generated method stub
		return search;
	}
}
