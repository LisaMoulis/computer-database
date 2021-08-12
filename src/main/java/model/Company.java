package model;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {

	@Id
	private int id = -1;
	@Column
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
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}
