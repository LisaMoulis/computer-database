package ui;

import java.util.ArrayList;
import java.util.Scanner;

import command.CommandHandler;

public class Main {

	private static CommandHandler commands = new CommandHandler();
	
	
	
	public static void main()
	{
		Scanner input = new Scanner(System.in);
		
		String sinput = input.nextLine();
		String[] cinputs = sinput.split(" ");
		commands.exec(cinputs);
	}
}
