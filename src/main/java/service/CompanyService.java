package service;

import java.util.ArrayList;

import model.Company;
import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;

public class CompanyService {

	private static CompanyService instance;
	
	private CompanyService()
	{}
	
	public static CompanyService getInstance()
	{
		if (instance == null)
		{
			instance = new CompanyService();
		}
		return instance;
	}
	
	public Company getCompany(int id)
	{
		return CompanyRequestHandler.getCompany(id);
	}
	
	public Company getCompany(String name)
	{
		return CompanyRequestHandler.getCompany(name);
	}
	
	public void removeCompany(int id)
	{
		ComputerRequestHandler.deleteComputer(id);
	}
	
	public void removeCompany(String name)
	{
		CompanyRequestHandler.deleteCompany(CompanyRequestHandler.getCompany(name).getId());
	}
	
	public ArrayList<Company> getAllCompanies()
	{
		return CompanyRequestHandler.getAllCompanies();
	}
}
