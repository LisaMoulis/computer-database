package command;

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
	public void exec(String... args) {
		System.out.println("Bye!");
		CLIReader.close();
	}
}
