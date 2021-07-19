package command;

import model.*;
import persistence.ComputerRequestHandler;
import service.displayer.Displayer;

/**
 * Class CommandDetails :
 * Display the details of a computer
 * @author Lisa
 */
public class CommandDetails extends Command {
	private final String name = "details";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		if (args.length == 2)
		{
			this.logger.debug("Details of the computer displayed.");
			return displayer.detailsComputer(args[1]);
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
