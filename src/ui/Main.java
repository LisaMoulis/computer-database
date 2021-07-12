package ui;

import command.CommandHandler;
import persistence.RequestHandler;

/**
 * Class Main
 * Entry point
 */
public class Main {

	private static CommandHandler commands = new CommandHandler();
	
	
	public static void main(String[] str)
	{
		System.out.println(RequestHandler.getComputer(5).toString());
		//getFromCommandLine();
	}
	
	private static void getFromCommandLine()
	{
		
		while (CLIReader.canRead())
		{
			commands.exec(CLIReader.getLine());
		}
	}
	
}
