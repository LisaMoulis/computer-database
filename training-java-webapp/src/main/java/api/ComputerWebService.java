package api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import dto.ComputerDTO;
import mapper.ComputerDTOMapper;
import service.ComputerService;

@RestController("/service/computers")
public class ComputerWebService {

	private ComputerService computerService;
	private ComputerDTOMapper computerMapper;
	
	@Autowired
	public void setComputerService(ComputerService computerService)
	{
		this.computerService = computerService;
	}
	
	@Autowired
	public void setComputerDTOMapper(ComputerDTOMapper computerDTOMapper)
	{
		this.computerMapper = computerDTOMapper;
	}
	
	@RequestMapping(params = {"id"}, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ComputerDTO getComputer(@RequestParam("id") int id)
	{
		return computerMapper.mapToDTO(computerService.getComputer(id));
	}
	
	@RequestMapping(params = {"name"}, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ComputerDTO getComputer(@RequestParam("name") String name)
	{
		return computerMapper.mapToDTO(computerService.getComputer(name));
	}
	

	@RequestMapping(method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void addComputer(@Valid ComputerDTO computer)
	{
		computerService.createComputer(computerMapper.mapToComputer(computer));
	}
	
	@RequestMapping(value= "/service/computers/update",method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void updateComputer(@Valid ComputerDTO computer)
	{
		computerService.updateComputer(computerMapper.mapToComputer(computer));
	}
	
	@RequestMapping(params = {"id"}, method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteComputer(@RequestParam("id") int id)
	{
		computerService.removeComputer(id);
	}
	
	@RequestMapping(params = {"name"}, method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteComputer(@RequestParam("name") String name)
	{
		computerService.removeComputer(name);
	}
}
