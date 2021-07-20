package ui;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import command.*;
import service.displayer.CLIDisplayer;
import service.displayer.Displayer;

/**
 * Class Main :
 * Entry point
 * @author Lisa
 */
public class Main {

	private static CommandHandler commands = CommandHandler.getInstance();
	protected static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static Displayer displayer = CLIDisplayer.getInstance();
	
	public static void main(String[] str)
	{
		logger.debug("Program started.");
		//System.out.println(ComputerRequestHandler.getComputer(5).toString());
		getFromCommandLine();
	}
	
	private static void getFromCommandLine()
	{
		//Read the command line inputs while it's possible
		while (CLIReader.canRead())
		{
			System.out.println("\nEnter your command :\n");
			System.out.println(commands.exec(displayer,CLIReader.getLine()));
		}
		logger.debug("Program closed.");
	}
	
}
