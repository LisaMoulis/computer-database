package command;

import service.displayer.Displayer;
import ui.CLIReader;

/**
 * Class CommandQuit :
 * Close the program
 * @author Lisa
 */
public class CommandQuit extends Command {

	private final String name = "quit";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		CLIReader.close();
		return displayer.quit();
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
