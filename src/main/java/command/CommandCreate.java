package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.*;
import persistence.ComputerRequestHandler;
import service.ComputerBuilder;
import service.displayer.Displayer;

/**
 * Class CommandCreate :
 * Create a computer
 * @author Lisa
 */
public class CommandCreate extends Command {
	private final String name = "create";
	
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String exec(Displayer displayer,String...args) {
		ComputerBuilder newone = new ComputerBuilder();
		for (int i = 1; i+1 < args.length;i+=2)
		{
			switch (args[i])
			{
			case ("name"):
				newone.setName(args[i+1]);
				break;
			case("introduced"):
				newone.setIntroduced(LocalDate.parse(args[i+1],DateTimeFormatter.ISO_LOCAL_DATE));
				break;
			case("discontinued"):
				newone.setDiscontinued(LocalDate.parse(args[i+1],DateTimeFormatter.ISO_LOCAL_DATE));
				break;
			case("company"):
				newone.setCompany(args[i+1]);
				break;
			}
		}
		System.out.println(args);
		this.logger.info("Computer created.");
		Computer newc = newone.build();
		this.logger.info(newc.toString());
		//Create the computer in the database and locally
		ComputerList.getInstance().add(newc);
		ComputerRequestHandler.createComputer(newc);
		return displayer.crud();
		
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (object == null || !this.name.equals(((Command)object).getName()))
		{
			return false;
		}
		return true;
	}
	
}
