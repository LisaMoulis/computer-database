package command;

import model.ComputerList;

/**
 * Class CommandNextPage :
 * Display the previous page of computers
 * @author Lisa
 */
public class CommandPreviousPage extends Command{
	private final String name = "previous";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(String... args) {
		if (args.length == 1)
		{
			System.out.println("List of the computers :\n");
			System.out.println(ComputerList.getInstance().previousPage());
			this.logger.debug("List of computers displayed.");
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
