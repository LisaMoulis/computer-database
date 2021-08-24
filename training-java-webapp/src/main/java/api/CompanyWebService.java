package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.CompanyService;


@RestController
@RequestMapping("/service/companies")
public class CompanyWebService {

	private CompanyService companyService;
	
	@Autowired
	public void setCompanyService(CompanyService companyService)
	{
		this.companyService = companyService;
	}
	
	@RequestMapping(params = {"id"}, method = RequestMethod.DELETE)
	public void deleteCompany(@RequestParam("id") int id)
	{
		companyService.removeCompany(id);
	}
	
	@RequestMapping(params = {"name"}, method = RequestMethod.DELETE)
	public void deleteCompany(@RequestParam("name") String name)
	{
		companyService.removeCompany(name);
	}
}
