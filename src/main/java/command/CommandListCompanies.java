package command;

import model.CompanyList;
import service.displayer.Displayer;

/**
 * Class CommandListCompanies :
 * Display a list of the companies
 * @author Lisa
 */
public class CommandListCompanies extends Command {
	private final String name = "companies";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		if (args.length == 1)
		{
			this.logger.debug("Displaying the list of the companies.");
			return displayer.listCompanies();
		}
		else
		{
			return "Mismatch of number of arguments\n";
		}
		
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !this.name.equals(((Command)object).getName()))
		{
			return false;
		}
		return true;
	}
}
