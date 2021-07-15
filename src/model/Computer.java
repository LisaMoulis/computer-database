package model;

import java.time.LocalDate;

/**
 * Class Computer :
 * Represent a computer and its information
 * @author Lisa
 */
public class Computer {
	private int id = -1;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String company;
	
	/**
	 * @param name
	 */
	public Computer(String name)
	{
		this.name = name;
	}
	/**
	 * @param name
	 * @param introduced
	 */
	public Computer(String name, LocalDate introduced)
	{
		this.name = name;
		this.introduced = introduced;		
	}
	
	/**
	 * @param name
	 * @param introduced
	 * @param company
	 */
	public Computer(String name, LocalDate introduced, String company)
	{
		this(name,introduced);
		this.company = company;
	}
	
	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 */
	public Computer(String name, LocalDate introduced, LocalDate discontinued)
	{
		this(name,introduced);
		if (discontinued != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
		
	}
	
	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(String name, LocalDate introduced, LocalDate discontinued, String company)
	{
		this(name,introduced,discontinued);
		this.company = company;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(int id,String name, LocalDate introduced, LocalDate discontinued, String company)
	{ 	this(name,introduced,discontinued,company);
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public LocalDate getIntroduced()
	{
		return this.introduced;
	}
	
	public void setIntroduced(LocalDate introduced)
	{
		if (this.discontinued == null || discontinued.isAfter(introduced))
		{
			this.introduced = introduced;
		}
	}
	
	public LocalDate getDiscontinued()
	{
		return this.discontinued;
	}
	
	public void setDiscontinued(LocalDate discontinued)
	{
		if (introduced != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
	}
	
	public String getCompany()
	{
		return this.company;
	}
	
	public void setCompany(String company)
	{
		this.company = company;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "\nComputer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued + ", company=" + company
				+ "]";
	}
}
