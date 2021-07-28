package dto;

import javax.servlet.http.HttpServletRequest;

import model.CompanyList;

public class ComputerDTO {

	private int id = -1;
	private String name;
	private String introduced;
	private String discontinued;
	private String company;
	private int companyId;
	
	public ComputerDTO(HttpServletRequest request)
	{
		this.name = (String) request.getParameter("computerName");
		this.introduced = (String) request.getParameter("introduced");
		this.discontinued = (String) request.getParameter("discontinued");
		if (!request.getParameter("companyId").equals("") && CompanyList.getInstance().getCompany(Integer.parseInt((String) request.getParameter("companyId")))!= null)
		{
			this.companyId = Integer.parseInt((String) request.getParameter("companyId"));
			this.company = CompanyList.getInstance().getCompany(this.companyId).getName();
		}
	}
	
	public ComputerDTO(int id, String name, String introduced, String discontinued, String company, int companyId)
	{
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
		this.companyId = companyId;
	}
	
	public int getId()
	{
		return this.id;
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
	
	public String getCompany()
	{
		return this.company;
	}
	
	public int getCompanyId()
	{
		return this.companyId;
	}

}
