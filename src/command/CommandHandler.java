package command;

import java.util.ArrayList;

public class CommandHandler {

	ArrayList<Command> commands;
	
	public CommandHandler()
	{
		this.commands = new ArrayList<Command>();
		this.commands.add(new CommandCreate());
		this.commands.add(new CommandDelete());
		this.commands.add(new CommandDetails());
		this.commands.add(new CommandListCompanies());
		this.commands.add(new CommandListComputers());
		this.commands.add(new CommandQuit());
		this.commands.add(new CommandUpdate());
	}
	
	public void exec(String...args)
	{
		for(Command c : commands)
		{
			if (c.getName().equals(args[0]))
			{
				c.exec(args);
			}
		}
	}
	
}
