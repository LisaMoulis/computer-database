package command;

import java.util.ArrayList;

import service.displayer.Displayer;

/**
 * Class CommandHandler :
 * Manage the commands and execute them
 * @author Lisa
 */
public class CommandHandler {

	ArrayList<Command> commands;
	
	public CommandHandler()
	{
		//Create the list of commands with the basic ones
		this.commands = new ArrayList<Command>();
		this.commands.add(new CommandCreate());
		this.commands.add(new CommandDelete());
		this.commands.add(new CommandDetails());
		this.commands.add(new CommandListCompanies());
		this.commands.add(new CommandListComputers());
		this.commands.add(new CommandUpdate());
		this.commands.add(new CommandQuit());
		this.commands.add(new CommandNextPage());
		this.commands.add(new CommandPreviousPage());
	}
	
	/**
	 * @param args	keyword of the entered command + arguments
	 */
	public String exec(Displayer displayer,String...args)
	{
		String str = null;
		//Calls the exec of the specified command
		for(Command c : commands)
		{
			if (c.getName().equals(args[0]))
			{
				str = c.exec(displayer, args);
			}
		}
		return str;
	}
	
	/**
	 * @param command	command to add
	 */
	public void add(Command command)
	{
		if (!commands.contains(command))
		{
			commands.add(command);
		}
	}
	
	/**
	 * @param command	command to remove
	 */
	public void remove(Command command)
	{
		if (commands.contains(command))
		{
			commands.remove(command);
		}
	}
	
	
	
}
