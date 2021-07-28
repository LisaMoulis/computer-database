package command;

import model.ComputerList;
import service.*;

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
	public void exec(String...args) {
		ComputerList page = CommandHandler.getInstance().getPage();
		if (args.length == 1)
		{
			page.setPage(1);
			this.logger.debug("Displaying the list of computers.");
			System.out.println("List of the computers :\n" + PageService.getInstance().getPage(page,"",""));
		}
		else if (args.length == 2)
		{
			this.logger.debug("Displaying the list of computers.");
			page.setPage(Integer.valueOf(args[2]));
			System.out.println("List of the computers :\n" + PageService.getInstance().getPage(page,"",""));
		}
		else
		{
			logger.error("Mismatch of number of arguments.");
			System.out.println("Mismatch of number of arguments.\n");
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
