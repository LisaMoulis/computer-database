package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.*;

/**
 * Class CommandUpdate :
 * Modify the information of a computer
 * @author Lisa
 */
public class CommandUpdate extends Command{
	private final String name = "update";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(CommandHandler handler, String...args) {
		Computer toupdate = null;
		try {
			int id = Integer.valueOf(args[1]);
			toupdate = computerService.getComputer(id);
		}
		catch (NumberFormatException e)
		{
			toupdate = computerService.getComputer(args[1]);
		}
		if (toupdate != null)
		{
			for (int i = 2; i+1 < args.length;i+=2)
			{
				switch (args[i])
				{
				case ("name"):
					toupdate.setName(args[i+1]);
					break;
				case("introduced"):
					toupdate.setIntroduced(LocalDate.parse(args[i+1],DateTimeFormatter.ISO_LOCAL_DATE));
					break;
				case("discontinued"):
					toupdate.setDiscontinued(LocalDate.parse(args[i+1],DateTimeFormatter.ISO_LOCAL_DATE));
					break;
				case("company"):
					toupdate.setCompany(args[i+1]);
					break;
				}
				
				int company_id = -1;

				Company company = companyRequestHandler.getCompany(toupdate.getCompany());
				if (company != null)
				{
					company_id = company.getId();
				}
				
				//Update the computer in the database
				computerRequestHandler.updateComputer(toupdate,company_id);
			}
			this.logger.debug("Computer info updated.");
			
		}
		else
		{
			this.logger.error("An issue in the computer update happened.");
		}
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
