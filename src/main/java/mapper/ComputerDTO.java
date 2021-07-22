package mapper;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import model.Company;
import model.CompanyList;

public class ComputerDTO {

	private String name;
	private String introduced;
	private String discontinued;
	private Company company;
	
	public ComputerDTO(HttpServletRequest request)
	{
		this.name = (String) request.getParameter("computerName");
		this.introduced = (String) request.getParameter("introduced");
		this.discontinued = (String) request.getParameter("discontinued");
		this.company = null;
		if (!request.getParameter("companyId").equals("") && CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getParameter("companyId")))!= null)
		{
			this.company = CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getParameter("companyId")));
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getIntroduced()
	{
		return this.introduced;
	}
	
	public String getDiscontinued()
	{
		return this.discontinued;
	}
	
	public Company getCompany()
	{
		return this.company;
	}
	
	public ArrayList<String> mapToCreateArgs()
	{
		ArrayList<String> args = new ArrayList<String>();
		args.add("create");
		args.add("name");
		args.add(name);
		if (introduced != null && !introduced.equals(""))
		{
			args.add("introduced");
			args.add(introduced);
		}
		if (discontinued != null && !discontinued.equals(""))
		{
			args.add("discontinued");
			args.add(discontinued);
		}
		if (company != null)
		{
			args.add("company");
			args.add(company.getName());
		}
		return args;
	}
}
