package model;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Class Computer :
 * Represent a computer and its information
 * @author Lisa
 */
@Entity
@Table(name = "computer")
public class Computer {
	@Id
	private int id = -1;
	@Column
	private String name;
	@Column(nullable=true)
	private LocalDate introduced;
	@Column(nullable=true)
	private LocalDate discontinued;
	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id",nullable=true)
	private Company company;
	
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
	 * @param discontinued
	 */
	public Computer(String name, LocalDate introduced, LocalDate discontinued)
	{
		this(name,introduced);
		this.discontinued = discontinued;
	}
	
	/**
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company)
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
	public Computer(int id,String name, LocalDate introduced, LocalDate discontinued, Company company)
	{ 	this(name,introduced,discontinued,company);
		this.id = id;
	}
	
	public Computer()
	{}

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
		this.introduced = introduced;
	}
	
	public LocalDate getDiscontinued()
	{
		return this.discontinued;
	}
	
	public void setDiscontinued(LocalDate discontinued)
	{
		this.discontinued = discontinued;
	}
	
	public Company getCompany()
	{
		return this.company;
	}
	
	public void setCompany(Company company)
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
	public boolean equals(Object c)
	{
		Computer totest = (Computer) c;
		if (totest == null || this.id != totest.getId())
		{
			return false;
		}
		else if (this.name != totest.getName() && this.name != null && !this.name.equals(totest.getName()))
		{
			return false;
		}
		else if (this.introduced != totest.getIntroduced() && this.introduced != null && !this.introduced.equals(totest.getIntroduced()))
		{
			return false;
		}
		else if (this.discontinued != totest.getDiscontinued() && this.discontinued != null && !this.discontinued.equals(totest.getDiscontinued()))
		{
			return false;
		}
		else if (this.company != null && !this.company.equals(totest.getCompany()))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Computer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued + ", company=" + company
				+ "]";
	}
}
