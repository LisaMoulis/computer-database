package ui;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import command.*;

/**
 * Class Main :
 * Entry point
 * @author Lisa
 */
public class Main {

	private static CommandHandler commands = CommandHandler.getInstance();
	protected static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] str)
	{
		logger.debug("Program started.");
		//System.out.println(ComputerRequestHandler.getComputer(5).toString());
		//ComputerBuilder builder = new ComputerBuilder();
		//System.out.println(builder.build());
		getFromCommandLine();
	}
	
	private static void getFromCommandLine()
	{
		//Read the command line inputs while it's possible
		while (CLIReader.canRead())
		{
			System.out.println("\nEnter your command :\n");
			commands.exec(CLIReader.getLine());
		}
		logger.debug("Program closed.");
	}
	
}
