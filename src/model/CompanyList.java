package model;

import java.util.HashMap;
import java.util.Map.Entry;

import persistence.CompanyRequestHandler;

/**
 * Class Computer
 * Represent a computer
 * @author Lisa
 */
public class CompanyList {

	private final HashMap<Integer,String> companies;
	private static CompanyList instance;
	
	private CompanyList()
	{
		companies = CompanyRequestHandler.getAllCompanies();
	}
	
	public static CompanyList getInstance()
	{
		if (instance == null)
		{
			instance = new CompanyList();
		}
		return instance;
	}
	
	

	public HashMap<Integer,String> getCompanies()
	{
		return this.companies;
	}
	
	public String getCompany(int id)
	{
		return companies.get(id);
	}
	
	public String getCompany(String name)
	{
		for (Entry<Integer, String> c : companies.entrySet())
		{
			if (c.getValue().equals(name))
			{
				return c.getValue();
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "CompanyList [companies=" + companies + "]";
	}
}
