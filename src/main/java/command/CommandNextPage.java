package command;

import model.ComputerList;
import service.displayer.Displayer;

/**
 * Class CommandNextPage :
 * Display the next page of computers
 * @author Lisa
 */
public class CommandNextPage extends Command{
	private final String name = "next";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		if (args.length == 1)
		{
			ComputerList.getInstance().nextPage();
			this.logger.debug("Next page of computers displayed.");
			return displayer.listComputers();
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
