package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Command :
 * Represent an executable command
 * @author Lisa
 */
public abstract class Command {

	protected final String name = "";
	protected final Logger logger = LoggerFactory.getLogger(Command.class);
	
	/**
	 * @param args	keyword of the command + arguments
	 */
	public abstract void exec(String... args);
	
	public abstract String getName();
	
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
