package command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected ComputerService computerService;
	protected CompanyService companyService;
	protected PageService pageService;
	protected ComputerRequestHandler computerRequestHandler;
	protected CompanyRequestHandler companyRequestHandler;
	
	public void setComputerService(ComputerService computerService)
	{
		this.computerService = computerService;
	}
	
	public void setCompanyService(CompanyService companyService)
	{
		this.companyService = companyService;
	}
	
	public void setPageService(PageService pageService)
	{
		this.pageService = pageService;
	}
	
	public void setComputerRequestHandler(ComputerRequestHandler computerRequestHandler)
	{
		this.computerRequestHandler = computerRequestHandler;
	}
	
	public void setCompanyRequestHandler(CompanyRequestHandler companyRequestHandler)
	{
		this.companyRequestHandler = companyRequestHandler;
	}
	
	/**
	 * @param args	keyword of the command + arguments
	 */
	public abstract void exec(CommandHandler handler,String...args);
	
	public abstract String getName();
	
	@Override
	public abstract boolean equals(Object object);
}
