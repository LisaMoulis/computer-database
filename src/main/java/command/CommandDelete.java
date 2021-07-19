package command;

import model.ComputerList;
import persistence.ComputerRequestHandler;

/**
 * Class CommandDelete :
 * Delete a computer
 * @author Lisa
 */
public class CommandDelete extends Command {

	private final String name = "delete";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(String... args) {
		if (args.length == 2)
		{
			try {
				int id = Integer.valueOf(args[1]);
				ComputerRequestHandler.deleteComputer(id);
				ComputerList.getInstance().remove(id);
			}
			catch (NumberFormatException e)
			{
				ComputerRequestHandler.deleteComputer(args[1]);
				ComputerList.getInstance().remove(args[1]);
			}
			this.logger.debug("Computer deleted.");
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
