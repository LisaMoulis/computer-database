package command;

/**
 * Class CommandListCompanies :
 * Display a list of the companies
 * @author Lisa
 */
public class CommandListCompanies extends Command {
	private final String name = "companies";

	public String getName()
	{
		return this.name;
	}
	
	@Override
	public void exec(CommandHandler handler, String...args) {
		if (args.length == 1)
		{
			this.logger.debug("Displaying the list of the companies.");
			System.out.println("List of the companies :\n" + companyService.toString());
		}
		else
		{
			logger.debug("Mismatch of number of arguments.");
			System.out.println("Mismatch of number of arguments.\n");
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
