package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Command :
 * Represent an executable command
 * @author Lisa
 */
public abstract class Command {

	protected final Logger logger = LoggerFactory.getLogger(Command.class);
	
	/**
	 * @param args	keyword of the command + arguments
	 */
	public abstract void exec(String...args);
	
	public abstract String getName();
	
	@Override
	public abstract boolean equals(Object object);
}
