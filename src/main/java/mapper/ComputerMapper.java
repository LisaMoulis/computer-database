package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import model.CompanyList;
import model.Computer;

/**
 * Class ComputerMapper :
 * Map the information between the database and computers
 * @author Lisa
 */
public class ComputerMapper {
	
	/**
	 * @param result	Representation of a computer from the database
	 * @return A computer object created from one of the database
	 * @throws SQLException
	 */
	public static Computer mapToComputer(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		LocalDate introduced = null;
		LocalDate discontinued = null;
		//Verify if some columns are empty before getting them
		if (result.getBytes("introduced") != null && !result.getString("introduced").equals("0000-00-00 00:00:00"))
		{
			introduced = result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
		}
		
		if (result.getBytes("discontinued") != null && !result.getString("discontinued").equals("0000-00-00 00:00:00"))
		{
			discontinued = result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
			
		}
		String company = result.getString("company.name");
		int id = result.getInt("computer.id");
		return new Computer(id,name,introduced,discontinued,company);
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
			values.append(", `company_id`='").append(CompanyList.getInstance().getCompany(c.getCompany()).getId()).append("'");
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
		StringBuilder columns = new StringBuilder("(`id`, `name`");
		StringBuilder values = new StringBuilder(") VALUES (").append(c.getId()).append(", '").append(c.getName()).append("'");
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
		if (c.getCompany() != null)
		{
			columns.append(", `company_id`");
			values.append(", ").append(CompanyList.getInstance().getCompany(c.getCompany()).getId());
		}
		values.append(")");
		return columns.append(values).toString();		
	}
}
