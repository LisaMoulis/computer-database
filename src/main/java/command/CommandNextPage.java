package command;

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
	public void exec(String... args) {
		if (args.length == 1)
		{
			System.out.println("Next page of computers :\n");
			System.out.println(ComputerList.getInstance().nextPage());
			this.logger.debug("Next page of computers displayed.");
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