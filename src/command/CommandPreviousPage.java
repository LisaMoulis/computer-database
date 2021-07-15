package command;

import model.ComputerList;

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
}
