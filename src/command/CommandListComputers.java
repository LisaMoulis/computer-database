package command;

import model.ComputerList;

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
	public void exec(String... args) {
		if (args.length == 1)
		{
			System.out.println("List of the computers :\n");
			System.out.println(ComputerList.getInstance().beginPage());
			this.logger.debug("List of computers displayed.");
		}
		else
		{
			System.out.println("Mismatch of number of arguments\n");
		}
	}	
}
