package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dto.ComputerDTO;
import model.Computer;
import service.ComputerBuilder;

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
			builder.setCompany(dto.getCompany().getName());
		}
		return builder.build();
	}
}
