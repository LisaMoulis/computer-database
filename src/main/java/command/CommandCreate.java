package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import builder.ComputerBuilder;
import model.*;
import service.Validator;

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
	public void exec(CommandHandler handler, String...args) {
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
		
		Computer newc = newone.build();
		//Create the computer in the database and locally
		Validator.validate(newc);
		
		int company_id = -1;

		Company company = companyRequestHandler.getCompany(newc.getCompany());
		if (company != null)
		{
			company_id = company.getId();
		}
		computerRequestHandler.createComputer(newc,company_id);
		this.logger.info("Computer created.");
		this.logger.info(newc.toString());
		System.out.println("Done.\n");
		
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
