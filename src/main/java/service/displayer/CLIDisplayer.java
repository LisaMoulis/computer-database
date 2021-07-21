package service.displayer;

import model.CompanyList;
import model.ComputerList;
import persistence.ComputerRequestHandler;

public class CLIDisplayer implements Displayer {

	private static CLIDisplayer instance;
	
	private CLIDisplayer()
	{}
	
	public static CLIDisplayer getInstance()
	{
		if (instance == null)
		{
			instance = new CLIDisplayer();
		}
		return instance;
	}
	
	@Override
	public String crud() {
		return "Done\n";
	}

	@Override
	public String listCompanies() {
		return "List of the companies :\n" + CompanyList.getInstance().toString();
	}

	@Override
	public String listComputers() {
		return "List of the computers :\n" + ComputerList.getInstance().getPage();
	}
	
	@Override
	public String listComputers(int index) {
		return "List of the computers :\n" + ComputerList.getInstance().getPage(index,10);
	}

	@Override
	public String detailsComputer(String arg) {
		String str = "Details of the computer :\n";
		try {
			int id = Integer.valueOf(arg);
			str += ComputerRequestHandler.getComputer(id);
		}
		catch (NumberFormatException e)
		{
			str += ComputerList.getInstance().getComputer(arg);
		}
		return str;
	}

	@Override
	public String nextComputers() {
		return "Next page of computers :\n" + CompanyList.getInstance().toString();
	}

	@Override
	public String previousComputers() {
		return "Previous page of computers :\n" + CompanyList.getInstance().toString();
	}

	@Override
	public String quit() {
		return "Bye!";
	}

}
