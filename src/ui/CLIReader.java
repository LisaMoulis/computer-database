package ui;

import java.util.Scanner;

/**
 * Class CLIReader
 * Read the commands from the command line
 */
public class CLIReader {

	private static boolean open = true;
	public static final Scanner input = new Scanner(System.in);
	
	public static void close()
	{
		open = false;
	}
	
	public static boolean canRead()
	{
		return open;
	}
	
	public static String[] getLine()
	{
		String[] line = null;
		if (open)
		{
			String sinput = input.nextLine();
			line = sinput.split(" ");
		}
		return line;
	}
}
