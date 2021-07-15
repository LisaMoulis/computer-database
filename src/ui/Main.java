package ui;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import command.Command;
import command.CommandHandler;
import persistence.ComputerRequestHandler;

/**
 * Class Main
 * Entry point
 */
public class Main {

	private static CommandHandler commands = new CommandHandler();
	protected static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] str)
	{
		logger.debug("Program started.");
		//System.out.println(ComputerRequestHandler.getComputer(5).toString());
		getFromCommandLine();
	}
	
	private static void getFromCommandLine()
	{
		
		while (CLIReader.canRead())
		{
			System.out.println("\nEnter your command :\n");
			commands.exec(CLIReader.getLine());
		}
		logger.debug("Program closed.");
	}
	
}
