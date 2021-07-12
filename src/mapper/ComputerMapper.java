package mapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

import model.Computer;
import persistence.DBConnection;

/**
 * Class ComputerMapper
 * Map the information between the database and computers
 */
public class ComputerMapper {
	
	public static Computer mapToComputer(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		LocalDate introduced = null;
		if (result.getDate("introduced") != null)
		{
			introduced = result.getDate("introduced").toLocalDate();
		}
		LocalDate discontinued = null;
		if (result.getDate("discontinued") != null)
		{
			discontinued = result.getDate("discontinued").toLocalDate();
			
		}
		String company = result.getString("company.name");
		
		return new Computer(name,introduced,discontinued,company);
	}
}
