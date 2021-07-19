package command;

import model.*;
import persistence.ComputerRequestHandler;

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
	public void exec(String... args) {
		if (args.length == 2)
		{
			System.out.println("Details of the computer :\n");
			try {
				int id = Integer.valueOf(args[1]);
				System.out.println(ComputerRequestHandler.getComputer(id));
			}
			catch (NumberFormatException e)
			{
				System.out.println(ComputerList.getInstance().getComputer(args[1]));
			}
			this.logger.debug("Details of the computer displayed.");
		}
		else
		{
			System.out.println("Mismatch of number of arguments\n");
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