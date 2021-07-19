package command;

import model.CompanyList;

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
	public void exec(String... args) {
		if (args.length == 1)
		{
			System.out.println("List of the companies :\n");
			System.out.println(CompanyList.getInstance().toString());
			this.logger.debug("List of the companies displayed");
		}
		else
		{
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
