package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import persistence.CompanyRequestHandler;
import persistence.ComputerRequestHandler;
import service.CompanyService;
import service.ComputerService;
import service.PageService;

/**
 * Class Command :
 * Represent an executable command
 * @author Lisa
 */

@Component
public abstract class Command {

	protected final Logger logger = LoggerFactory.getLogger(Command.class);
	@Autowired
	protected ComputerService computerService;
	@Autowired
	protected CompanyService companyService;
	@Autowired
	protected PageService pageService;
	@Autowired
	protected ComputerRequestHandler computerRequestHandler;
	@Autowired
	protected CompanyRequestHandler companyRequestHandler;
	
	/**
	 * @param args	keyword of the command + arguments
	 */
	public abstract void exec(String...args);
	
	public abstract String getName();
	
	@Override
	public abstract boolean equals(Object object);
}
