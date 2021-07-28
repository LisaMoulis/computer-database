package command;

import service.CompanyService;

/**
 * Class CommandDelete :
 * Delete a computer
 * @author Lisa
 */
public class CommandDeleteCompany extends Command {

	private final String name = "delete-company";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(String...args) {
		System.out.println("coucou");
		if (args.length == 2)
		{
			try {
				int id = Integer.valueOf(args[1]);
				CompanyService.getInstance().removeCompany(id);
			}
			catch (NumberFormatException e)
			{
				CompanyService.getInstance().removeCompany(args[1]);
			}
			this.logger.debug("Company deleted.");
		}
		else
		{
			this.logger.error("Mismatch of number of arguments.");
			System.out.println("Mismatch of number of arguments\n");
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
