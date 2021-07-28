package command;

import service.*;
import model.ComputerList;

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
	public void exec(String...args) {
		if (args.length == 1)
		{
			ComputerList page = CommandHandler.getInstance().getPage();
			page.setPage(page.getPage()+1);
			this.logger.debug("Next page of computers displayed.");
			System.out.println("List of the computers :\n" + PageService.getInstance().getPage(page,"","computer.id"));
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
