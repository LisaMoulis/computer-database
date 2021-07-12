package model;

import java.time.LocalDate;

/**
 * Class Computer
 * Represent a computer and its information
 */
public class Computer {
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String company;
	
	public Computer(String name)
	{
		this.name = name;
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
		if (discontinued != null && discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
		this.company = company;
	}
	
	public Computer(String name, LocalDate introduced, String company)
	{
		this.name = name;
		this.introduced = introduced;
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
	
	public LocalDate getDate()
	{
		return this.introduced;
	}
	
	public void setDate(LocalDate introduced)
	{
		if (discontinued.isAfter(introduced))
		{
			this.introduced = introduced;
		}
	}
	
	public LocalDate getEndDate()
	{
		return this.discontinued;
	}
	
	public void setEndDate(LocalDate discontinued)
	{
		if (discontinued.isAfter(introduced))
		{
			this.discontinued = discontinued;
		}
	}
	
	public String getcompany()
	{
		return this.company;
	}
	
	public void setcompany(String company)
	{
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "Computer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued + ", company=" + company
				+ "]";
	}
}
