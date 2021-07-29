package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
public class ComputerDTOMapper {
	
	/**
	 * @param result	Representation of a computer from the database
	 * @return A computer object created from one of the database
	 * @throws SQLException
	 */
	public static Computer mapToComputer(ComputerDTO dto)
	{
		ComputerBuilder builder = new ComputerBuilder().setName(dto.getName());
		//Verify if some columns are empty before getting them
		if (dto.getIntroduced() != null && dto.getIntroduced() != "")
		{
			builder.setIntroduced(LocalDate.parse(dto.getIntroduced(), DateTimeFormatter.ISO_LOCAL_DATE));
		}
		
		if (dto.getIntroduced() != null && dto.getIntroduced() != "")
		{
			builder.setDiscontinued(LocalDate.parse(dto.getIntroduced(), DateTimeFormatter.ISO_LOCAL_DATE));
		}
		if (dto.getCompany() != null)
		{
			builder.setCompany(dto.getCompany());
		}
		return builder.build();
	}
	
	public static ComputerDTO mapToDTO(Computer computer)
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
			Company comp = CompanyService.getInstance().getCompany(computer.getCompany());
			if (comp != null)
			{
				builder.setCompany(comp.getName()).setCompanyId(comp.getId());
			}
		}
		return builder.build();
	}
	
	public static List<ComputerDTO> mapToDTOList(List<Computer> computers)
	{
		List<ComputerDTO> dto = (List<ComputerDTO>) computers.stream().map(e -> mapToDTO(e)).toList();
		return dto;
	}
}
