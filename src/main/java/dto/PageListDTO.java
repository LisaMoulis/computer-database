package dto;

import java.util.List;

public class PageListDTO {
	
	private int page = 1;
	private int size = 10;
	private String search = "";
	private String order = "computer.name";
	private int nbComputers = 1;
	private String sense = "asc";
	
	private List<ComputerDTO> computers;
	
	public int getPage()
	{
		return this.page;
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
	
	public int getSize()
	{
		return this.size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
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
	
	public List<ComputerDTO> getComputers()
	{
		return this.computers;
	}
	
	public void setComputers(List<ComputerDTO> computers)
	{
		this.computers = computers;
	}
	
	public int getNbComputers()
	{
		return this.nbComputers;
	}
	
	public void setNbComputers(int nb)
	{
		this.nbComputers = nb;
	}
	
	public String getOrder()
	{
		return this.order;
	}
	
	public void setOrder(String o)
	{
		this.order = o;
	}
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String s)
	{
		this.search = s;
	}

	public String getSense()
	{
		return this.sense;
	}
	
	public void setSense(String str)
	{
		this.sense = str;
	}
}
