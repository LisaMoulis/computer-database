package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.CompanyList;
import model.Computer;

/**
 * Class ComputerMapper
 * Map the information between the database and computers
 */
public class ComputerMapper {
	
	public static Computer mapToComputer(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		LocalDate introduced = null;
		if (result.getBytes("introduced") != null && !result.getString("introduced").equals("0000-00-00 00:00:00"))
		{
			introduced = result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
		}
		LocalDate discontinued = null;
		if (result.getBytes("discontinued") != null && !result.getString("discontinued").equals("0000-00-00 00:00:00"))
		{
			discontinued = result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
			
		}
		String company = result.getString("company.name");
		int id = result.getInt("computer.id");
		return new Computer(id,name,introduced,discontinued,company);
	}
	
	public static String mapToUpdate(Computer c)
	{
		
		StringBuilder values = new StringBuilder("`id`='").append(c.getId()).append("', `name`='").append(c.getName()).append("'");
		if (c.getIntroduced() != null)
		{
			values.append(", `introduced`=").append(Timestamp.valueOf(LocalDateTime.of(c.getIntroduced(),LocalTime.of(0, 0))));
		}
		if (c.getDiscontinued() != null)
		{
			values.append(", `discontinued`=").append(Timestamp.valueOf(LocalDateTime.of(c.getDiscontinued(),LocalTime.of(0, 0))));
		}
		if (c.getCompany() != null)
		{
			values.append(", `company_id`='").append(CompanyList.getInstance().getCompany(c.getCompany())).append("'");
		}
		return values.toString();	
	}
	
	public static String mapToCreate(Computer c)
	{
		StringBuilder columns = new StringBuilder("(`id`, `name`");
		StringBuilder values = new StringBuilder(") VALUES (").append(c.getId()).append(", '").append(c.getName()).append("'");
		if (c.getIntroduced() != null)
		{
			columns.append(", `introduced`");
			values.append(", ").append(Timestamp.valueOf(LocalDateTime.of(c.getIntroduced(),LocalTime.of(0, 0))));
		}
		if (c.getDiscontinued() != null)
		{
			columns.append(", `discontinued`");
			values.append(", ").append(Timestamp.valueOf(LocalDateTime.of(c.getDiscontinued(),LocalTime.of(0, 0))));
		}
		if (c.getCompany() != null)
		{
			columns.append(", `company_id`");
			values.append(", ").append(CompanyList.getInstance().getCompany(c.getCompany()));
		}
		values.append(")");
		return columns.append(values).toString();		
	}
}
