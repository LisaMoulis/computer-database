package model;

import java.util.ArrayList;

/**
 * Class CompanyList :
 * Keeper of the companies list
 * @author Lisa
 */
public class CompanyList {

	private ArrayList<Company> companies;
	
	public CompanyList()
	{
	}
	
	/**
	 * @param id Identifier of a company
	 * @return The company found
	 */
	public ArrayList<Company> getCompanies()
	{
		return this.companies;
	}
	
	public void setCompanies(ArrayList<Company> c)
	{
		this.companies = c;
	}
	
	
	@Override
	public String toString() {
		return "CompanyList [companies=" + companies + "]";
	}
}
