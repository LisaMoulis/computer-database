package model;

import java.time.LocalDate;

/**
 * Class Computer
 * Represent a computer and its information
 */
public class Computer {
	private String name;
	private LocalDate date;
	private LocalDate enddate;
	private String manufacturer;
	
	public Computer(String name)
	{
		this.name = name;
	}

	public Computer(String name, LocalDate date, LocalDate enddate)
	{
		this.name = name;
		this.date = date;
		if (enddate.isAfter(date))
		{
			this.enddate = enddate;
		}
		
	}
	
	public Computer(String name, LocalDate date, LocalDate enddate, String manufacturer)
	{
		this.name = name;
		this.date = date;
		if (enddate.isAfter(date))
		{
			this.enddate = enddate;
		}
		this.manufacturer = manufacturer;
	}
	
	public Computer(String name, LocalDate date, String manufacturer)
	{
		this.name = name;
		this.date = date;
		this.manufacturer = manufacturer;
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
		return this.date;
	}
	
	public void setDate(LocalDate date)
	{
		if (enddate.isAfter(date))
		{
			this.date = date;
		}
	}
	
	public LocalDate getEndDate()
	{
		return this.enddate;
	}
	
	public void setEndDate(LocalDate enddate)
	{
		if (enddate.isAfter(date))
		{
			this.enddate = enddate;
		}
	}
	
	public String getManufacturer()
	{
		return this.manufacturer;
	}
	
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
}
