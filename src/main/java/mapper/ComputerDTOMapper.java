package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.ComputerDTO;
import model.Company;
import model.Computer;
import service.CompanyService;
import builder.*;

/**
 * Class ComputerMapper :
 * Map the information between the database and computers
 * @author Lisa
 */

@Component
public class ComputerDTOMapper {
	
	private CompanyService companyService;
	
	@Autowired
	public ComputerDTOMapper(CompanyService companyService)
	{
		this.companyService = companyService;
	}
	
	/**
	 * @param result	Representation of a computer from the database
	 * @return A computer object created from one of the database
	 * @throws SQLException
	 */
	public Computer mapToComputer(ComputerDTO dto)
	{
		ComputerBuilder builder = new ComputerBuilder().setName(dto.getName());
		//Verify if some columns are empty before getting them
		if (dto.getIntroduced() != null && dto.getIntroduced() != "")
		{
			builder.setIntroduced(LocalDate.parse(dto.getIntroduced(), DateTimeFormatter.ISO_LOCAL_DATE));
		}
		
		if (dto.getDiscontinued() != null && dto.getDiscontinued() != "")
		{
			builder.setDiscontinued(LocalDate.parse(dto.getDiscontinued(), DateTimeFormatter.ISO_LOCAL_DATE));
		}
		if (dto.getCompany() != null)
		{
			builder.setCompany(dto.getCompany());
		}
		return builder.build();
	}
	
	public ComputerDTO mapToDTO(Computer computer)
	{
		ComputerDTOBuilder builder = new ComputerDTOBuilder().setId(computer.getId()).setName(computer.getName());
		if (computer.getIntroduced() != null )
		{
			builder.setIntroduced(computer.getIntroduced().format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
		if (computer.getDiscontinued() != null)
		{
			builder.setDiscontinued(computer.getDiscontinued().format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
		if (computer.getCompany() != null)
		{
			Company comp = companyService.getCompany(computer.getCompany());
			if (comp != null)
			{
				builder.setCompany(comp.getName()).setCompanyId(comp.getId());
			}
		}
		return builder.build();
	}
	
	public List<ComputerDTO> mapToDTOList(List<Computer> computers)
	{
		List<ComputerDTO> dto = (List<ComputerDTO>) computers.stream().map(e -> mapToDTO(e)).toList();
		return dto;
	}
}
