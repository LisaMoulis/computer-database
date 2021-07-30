package mapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

import builder.ComputerBuilder;
import model.*;
import service.*;

/**
 * Class ComputerMapper :
 * Map the information between the database and computers
 * @author Lisa
 */
public class ComputerDAOMapper {
	
	/**
	 * @param result	Representation of a computer from the database
	 * @return A computer object created from one of the database
	 * @throws SQLException
	 */
	public static Computer mapToComputer(ResultSet result) throws SQLException
	{
		ComputerBuilder builder = new ComputerBuilder().setName(result.getString("name"));
		//Verify if some columns are empty before getting them
		if (result.getBytes("introduced") != null && !result.getString("introduced").equals("0000-00-00 00:00:00"))
		{
			builder.setIntroduced(result.getTimestamp("introduced").toLocalDateTime().toLocalDate());
		}
		
		if (result.getBytes("discontinued") != null && !result.getString("discontinued").equals("0000-00-00 00:00:00"))
		{
			builder.setDiscontinued(result.getTimestamp("discontinued").toLocalDateTime().toLocalDate());
			
		}
		builder.setCompany(result.getString("company.name"));
		builder.setId(result.getInt("computer.id"));
		return builder.build();
	}
	
	/**
	 * @param computer	Computer to convert into a database representation
	 * @return String representing the computer in an update request
	 */
	public static String mapToUpdate(Computer c)
	{
		//Create the part of an update request with the computer values
		StringBuilder values = new StringBuilder("`id`='").append(c.getId()).append("', `name`='").append(c.getName()).append("'");
		if (c.getIntroduced() != null)
		{
			values.append(", `introduced`='").append(Timestamp.valueOf(LocalDateTime.of(c.getIntroduced(),LocalTime.of(0, 0)))).append("'");
		}
		if (c.getDiscontinued() != null)
		{
			values.append(", `discontinued`='").append(Timestamp.valueOf(LocalDateTime.of(c.getDiscontinued(),LocalTime.of(0, 0)))).append("'");
		}
		
		if (c.getCompany() != null)
		{
			Company comp = CompanyService.getInstance().getCompany(c.getCompany());
			values.append(", `company_id`='").append(comp.getId()).append("'");
		}
		return values.toString();	
	}
	
	/**
	 * @param computer	Computer to convert into a database representation
	 * @return String representing the computer in a create request
	 */
	public static String mapToCreate(Computer c)
	{
		//Create the part of a create request with the computer values
		StringBuilder columns = new StringBuilder("(`name`");
		StringBuilder values = new StringBuilder(") VALUES ('").append(c.getName()).append("'");
		if (c.getIntroduced() != null)
		{
			columns.append(", `introduced`");
			values.append(", '").append(Timestamp.valueOf(LocalDateTime.of(c.getIntroduced(),LocalTime.of(0, 0)))).append("'");
		}
		if (c.getDiscontinued() != null)
		{
			columns.append(", `discontinued`");
			values.append(", '").append(Timestamp.valueOf(LocalDateTime.of(c.getDiscontinued(),LocalTime.of(0, 0)))).append("'");
		}
		if (c.getCompany() != null && CompanyService.getInstance().getCompany(c.getCompany()) != null)
		{
			columns.append(", `company_id`");
			values.append(", ").append(CompanyService.getInstance().getCompany(c.getCompany()).getId());
		}
		values.append(")");
		return columns.append(values).toString();		
	}
}
