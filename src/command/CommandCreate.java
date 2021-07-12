package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Computer;
import model.ComputerList;
import persistence.ComputerRequestHandler;

/**
 * Class CommandCreate
 * Create a computer
 */
public class CommandCreate extends Command {
	private final String name = "create";
	
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(String... args) {
		Computer newone = null;
		switch (args.length)
		{
		case (1):
			newone = new Computer(args[0]);
			break;
		case (2):
			newone = new Computer(args[0],LocalDate.parse(args[1],DateTimeFormatter.ISO_LOCAL_DATE));
			break;
		case (3):
			try {
				newone = new Computer(args[0],LocalDate.parse(args[1],DateTimeFormatter.ISO_LOCAL_DATE),LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE));
			}
			catch (DateTimeParseException e)
			{
				newone = new Computer(args[0],LocalDate.parse(args[1],DateTimeFormatter.ISO_LOCAL_DATE),args[2]);
			}
			break;
		case (4):
			newone = new Computer(args[0],LocalDate.parse(args[1],DateTimeFormatter.ISO_LOCAL_DATE),LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE),args[3]);
			break;
		default:
				
		}
		if (newone != null)
		{
			ComputerRequestHandler.createComputer(newone);
		}
		
	}
	
}
