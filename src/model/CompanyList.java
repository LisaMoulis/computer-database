package model;

import java.util.HashMap;

/**
 * Class ComputerLists
 * Keeper of the computers list
 */
public class CompanyList {

	private final HashMap<Integer,String> companies;
	
	public CompanyList(HashMap<Integer,String> companies)
	{
		this.companies = companies;
	}
	
	public HashMap<Integer,String> getCompanies()
	{
		return this.companies;
	}
	
	public String getCompany(int id)
	{
		return companies.get(id);
	}
}
