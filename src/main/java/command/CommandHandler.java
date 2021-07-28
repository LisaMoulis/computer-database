package command;

import java.util.ArrayList;

import model.ComputerList;

/**
 * Class CommandHandler :
 * Manage the commands and execute them
 * @author Lisa
 */
public class CommandHandler {

	private ArrayList<Command> commands;
	private static CommandHandler instance;
	private ComputerList computerList = new ComputerList();
	
	private CommandHandler()
	{
		//Create the list of commands with the basic ones
		this.commands = new ArrayList<Command>();
		this.commands.add(new CommandCreate());
		this.commands.add(new CommandDelete());
		this.commands.add(new CommandDeleteCompany());
		this.commands.add(new CommandDetails());
		this.commands.add(new CommandListCompanies());
		this.commands.add(new CommandListComputers());
		this.commands.add(new CommandUpdate());
		this.commands.add(new CommandQuit());
		this.commands.add(new CommandNextPage());
		this.commands.add(new CommandPreviousPage());
	}
	
	public static CommandHandler getInstance()
	{
		if (instance == null)
		{
			instance = new CommandHandler();
		}
		return instance;
	}
	
	/**
	 * @param args	keyword of the entered command + arguments
	 */
	public void exec(String...args)
	{
		//Calls the exec of the specified command
		for(Command c : commands)
		{
			if (c.getName().equals(args[0]))
			{
				c.exec(args);
			}
		}
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
	
	public ComputerList getPage()
	{
		return this.computerList;
	}
	
}
