package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.*;
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
		case (2):
			newone = new Computer(args[1]);
			break;
		case (3):
			newone = new Computer(args[1],LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE));
			break;
		case (4):
			try {
				newone = new Computer(args[1],LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE),LocalDate.parse(args[3],DateTimeFormatter.ISO_LOCAL_DATE));
			}
			catch (DateTimeParseException e)
			{
				newone = new Computer(args[1],LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE),args[3]);
			}
			break;
		case (5):
			newone = new Computer(args[1],LocalDate.parse(args[2],DateTimeFormatter.ISO_LOCAL_DATE),LocalDate.parse(args[3],DateTimeFormatter.ISO_LOCAL_DATE),args[4]);
			break;
		default:
			System.out.println("Mismatch of number of arguments\n");
		}
		if (newone != null)
		{
			this.logger.info("Computer created.");
			ComputerList.getInstance().add(newone);
			ComputerRequestHandler.createComputer(newone);
			
		}
		else
		{
			this.logger.info("An issue in the computer creation happened.");
		}
		
	}
	
}
