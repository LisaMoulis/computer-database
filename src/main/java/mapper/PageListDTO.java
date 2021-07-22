package mapper;

import java.io.Serializable;
import java.util.List;

import model.*;

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
		this.computers = ComputerList.getInstance().getComputers();
	}
	
	public PageListDTO(int page, int size)
	{
		this.page = page;
		this.size = size;
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
		if (computers.size()%size == 0)
		{
			return computers.size()/size;
		}
		return computers.size()/size + 1;
	}
	
	public List<Computer> getComputers()
	{
		if (page*size > computers.size())
		{
			return computers.subList((page-1)*size, computers.size());
		}
		return computers.subList((page-1)*size, page*size);
	}
	
	public int getNbComputers()
	{
		return computers.size();
	}
	
	public void setPage(int page)
	{
		if (page < 1)
		{
			page = 1;
		}
		else if (page > getNbPages())
		{
			page = getNbPages();
		}
		this.page = page;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
}
