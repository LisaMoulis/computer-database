package service;

import java.util.ArrayList;

import model.Company;
import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

@Component("companyService")
@Scope("singleton")
public class CompanyService {

	//private static CompanyService instance;
	@Autowired
	private ComputerRequestHandler computerRequestHandler;
	@Autowired
	private CompanyRequestHandler companyRequestHandler;
	
	
	public CompanyService()
	{}
	
	/*public static CompanyService getInstance()
	{
		if (instance == null)
		{
			instance = new CompanyService();
		}
		return instance;
	}*/
	
	public Company getCompany(int id)
	{
		return CompanyRequestHandler.getCompany(id);
	}
	
	public Company getCompany(String name)
	{
		return companyRequestHandler.getCompany(name);
	}
	
	public void removeCompany(int id)
	{
		computerRequestHandler.deleteComputer(id);
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
