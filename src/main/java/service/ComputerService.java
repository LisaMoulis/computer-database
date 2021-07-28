package service;

import model.Computer;
import persistence.ComputerRequestHandler;

public class ComputerService {

	private static ComputerService instance;
	
	private ComputerService()
	{}
	
	public static ComputerService getInstance()
	{
		if (instance == null)
		{
			instance = new ComputerService();
		}
		return instance;
	}
	
	public Computer getComputer(int id)
	{
		return ComputerRequestHandler.getComputer(id);
	}
	
	public Computer getComputer(String name)
	{
		return ComputerRequestHandler.getComputer(name);
	}
	
	public void removeComputer(int id)
	{
		ComputerRequestHandler.deleteComputer(id);
	}
	
	public void removeComputer(String name)
	{
		ComputerRequestHandler.deleteComputer(name);
	}
	
	public void removeSelectedComputer(String[] computers)
	{
		for (String nb : computers)
		{
			ComputerRequestHandler.deleteComputer(Integer.valueOf(nb));
		}
	}
	
	public void createComputer(Computer computer) throws RuntimeException
	{	
		Validator.validate(computer);
		ComputerRequestHandler.createComputer(computer);
	}
	
	public void updateComputer(Computer computer) throws RuntimeException
	{
		Validator.validate(computer);
		ComputerRequestHandler.updateComputer(computer);
	}
}
