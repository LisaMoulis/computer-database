package model;

import java.time.LocalDate;

/**
 * Class Computer
 * Represent a computer and its information
 */
public class Computer {
	private int id = -1;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String company;
	
	public Computer(String name)
	{
		this.name = name;
	}
	
	public Computer(String name, LocalDate introduced)
	{
		this.name = name;
		this.introduced = introduced;		
	}
	
	public Computer(String name, LocalDate introduced, String company)
	{
		this.name = name;
		this.introduced = introduced;
		this.company = company;
	}
	
	public Computer(String name, LocalDate introduced, LocalDate discontinued)
	{
		this.name = name;
		this.introduced = introduced;
		if (discontinued != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
		
	}
	
	public Computer(String name, LocalDate introduced, LocalDate discontinued, String company)
	{
		this.name = name;
		this.introduced = introduced;
		if (discontinued != null && introduced != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
		this.company = company;
	}
	
	public Computer(int id,String name, LocalDate introduced, LocalDate discontinued, String company)
	{
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		if (discontinued != null && introduced != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
		this.company = company;
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
