package service;

import model.Computer;
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
	
	public void removeCompany(int id)
	{
		ComputerRequestHandler.deleteComputer(id);
	}
	
	public void removeCompany(String name)
	{
		CompanyRequestHandler.deleteCompany(CompanyRequestHandler.getCompany(name).getId());
	}
}
