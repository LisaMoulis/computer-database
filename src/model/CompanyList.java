package model;

import java.util.HashMap;
import java.util.Map.Entry;

import persistence.CompanyRequestHandler;

/**
 * Class CompanyList :
 * Keeper of the companies list
 * @author Lisa
 */
public class CompanyList {

	private final HashMap<Integer,String> companies;
	private static CompanyList instance;
	
	private CompanyList()
	{
		companies = CompanyRequestHandler.getAllCompanies();
	}
	
	/**
	 * @return instance	The unique instance of the class
	 */
	public static CompanyList getInstance()
	{
		//Create the instance if it's not existing
		if (instance == null)
		{
			instance = new CompanyList();
		}
		return instance;
	}
	
	/**
	 * @param id Identifier of a company
	 * @return The company found
	 */
	public HashMap<Integer,String> getCompanies()
	{
		return this.companies;
	}
	
	public String getCompany(int id)
	{
		return companies.get(id);
	}
	
	/**
	 * @param name Name of a company
	 * @return The company found
	 */
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
