package service;

import java.time.LocalDate;

import model.Computer;

public class ComputerBuilder {

	private int id = -1;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private String company;

	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setIntroduced(LocalDate introduced)
	{
		this.introduced = introduced;
	}
	
	public void setDiscontinued(LocalDate discontinued)
	{
		this.discontinued = discontinued;
	}
	
	public void setCompany(String company)
	{
		this.company = company;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Computer build()
	{
		return new Computer(id, name, introduced, discontinued, company);
	}
}
