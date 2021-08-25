package command;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import mapper.ComputerDTOMapper;
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
	protected ComputerDTOMapper computerMapper = new ComputerDTOMapper();
	protected Client client;
	protected static final String APP_URI = "http://localhost:8080/training-java-webapp/service";
	
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
	
	public void setComputerDTOMapper(ComputerDTOMapper computerMapper)
	{
		this.computerMapper = computerMapper;
	}
	
	
	public void setClient(Client client)
	{
		this.client = client;
	}
	
	/**
	 * @param args	keyword of the command + arguments
	 */
	public abstract void exec(CommandHandler handler,String...args);
	
	public abstract String getName();
	
	@Override
	public abstract boolean equals(Object object);

	
}
