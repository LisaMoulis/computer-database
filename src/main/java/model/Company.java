package model;

public class Company {

	private int id = -1;
	private String name;
	
	public Company(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public Company(String name)
	{
		this.name = name;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
}
