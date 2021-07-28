package command;

import persistence.ComputerRequestHandler;
import service.ComputerService;

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
	public void exec(String...args) {
		if (args.length == 2)
		{
			try {
				int id = Integer.valueOf(args[1]);
				ComputerRequestHandler.deleteComputer(id);
				ComputerService.getInstance().removeComputer(id);
			}
			catch (NumberFormatException e)
			{
				ComputerRequestHandler.deleteComputer(args[1]);
				ComputerService.getInstance().removeComputer(args[1]);
			}
			this.logger.debug("Computer deleted.");
		}
		else
		{
			this.logger.error("Mismatch of number of arguments.");
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
