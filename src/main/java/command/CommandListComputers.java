package command;

import model.ComputerList;
import service.displayer.Displayer;

/**
 * Class CommandListComputers :
 * Display a list of the computers
 * @author Lisa
 */
public class CommandListComputers extends Command{
	private final String name = "computers";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		if (args.length == 1)
		{
			ComputerList.getInstance().beginPage();
			this.logger.debug("Displaying the list of computers.");
			return displayer.listComputers();
		}
		else if (args.length == 2)
		{
			this.logger.debug("Displaying the list of computers.");
			return displayer.listComputers(Integer.valueOf(args[2]));
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
