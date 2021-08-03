package service;

import model.Computer;
import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

@Component("computerService")
@Scope("singleton")
public class ComputerService {

	//private static ComputerService instance;
	@Autowired
	private ComputerRequestHandler computerRequestHandler;
	@Autowired
	private CompanyRequestHandler companyRequestHandler;
	
	public ComputerService()
	{}
	
	/*public static ComputerService getInstance()
	{
		if (instance == null)
		{
			instance = new ComputerService();
		}
		return instance;
	}*/
	
	public Computer getComputer(int id)
	{
		return computerRequestHandler.getComputer(id);
	}
	
	public Computer getComputer(String name)
	{
		return computerRequestHandler.getComputer(name);
	}
	
	public void removeComputer(int id)
	{
		computerRequestHandler.deleteComputer(id);
	}
	
	public void removeComputer(String name)
	{
		computerRequestHandler.deleteComputer(name);
	}
	
	public void removeSelectedComputer(String[] computers)
	{
		for (String nb : computers)
		{
			computerRequestHandler.deleteComputer(Integer.valueOf(nb));
		}
	}
	
	public void createComputer(Computer computer) throws RuntimeException
	{	
		Validator.validate(computer);
		computerRequestHandler.createComputer(computer);
	}
	
	public void updateComputer(Computer computer) throws RuntimeException
	{
		Validator.validate(computer);
		computerRequestHandler.updateComputer(computer);
	}
}
