package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Computer;

/**
 * Class CompanyMapper
 * Map the information between the database and companies
 */
public class CompanyMapper {

	public static String mapToCompany(ResultSet result) throws SQLException
	{
		String name = result.getString("name");
		return name;
	}
}
